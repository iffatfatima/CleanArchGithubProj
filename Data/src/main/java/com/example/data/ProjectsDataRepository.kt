package com.example.data

import com.example.data.mapper.ProjectMapper
import com.example.data.repository.ProjectsCache
import com.example.data.store.ProjectsDataStoreFactory
import com.example.domain.model.Project
import com.teamo.clean.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val projectsDataStoreFactory: ProjectsDataStoreFactory
) : ProjectsRepository{

    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(cache.areProjectsCached().toObservable(),
            cache.isProjectCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areProjectsCached, isExpired ->
                Pair(areProjectsCached, isExpired)
            })
            .flatMap{selectionPair ->
                projectsDataStoreFactory.getDataStore(selectionPair.first, selectionPair.second).getProjects()
            }
            .flatMap{projectsList ->
                projectsDataStoreFactory.getCacheDataStore()
                    .saveProjects(projectsList)
                    .andThen(Observable.just(projectsList))
            }
            .map { projectsList ->
                projectsList.map{ projectEntity ->
                    mapper.mapFromEntity(projectEntity)
                }
            }
    }


    override fun bookmarkProject(projectId: String): Completable {
        return projectsDataStoreFactory.getCacheDataStore().setProjectAsBookmarked(projectId)
    }

    override fun unbookmarkProject(projectId: String): Completable {
        return projectsDataStoreFactory.getCacheDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return projectsDataStoreFactory.getCacheDataStore().getBookmarkedProjects()
            .map {projectEntitiy ->
                projectEntitiy.map { project ->
                    mapper.mapFromEntity(project)
                }
            }
    }

}
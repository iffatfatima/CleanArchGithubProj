package com.example.data

import com.example.data.mapper.ProjectMapper
import com.example.data.repository.ProjectsCache
import com.example.data.store.ProjectsDataStoreFactory
import com.example.domain.model.Project
import com.example.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val projectsDataStoreFactory: ProjectsDataStoreFactory
) : ProjectsRepository {

    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(
            cache.areProjectsCached().toObservable(),
            cache.isProjectCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>>{areCached, isExpired ->
                Pair(areCached, isExpired)
            })
            .flatMap {
                projectsDataStoreFactory.getDataStore(it.first, it.second).getProjects()
            }.flatMap { projects ->
                projectsDataStoreFactory.
                    getCacheDataStore().
                    saveProjects(projects)
                    .andThen(Observable.just(projects))
            }.map { it.map { project ->
                mapper.mapFromEntity(project) }
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
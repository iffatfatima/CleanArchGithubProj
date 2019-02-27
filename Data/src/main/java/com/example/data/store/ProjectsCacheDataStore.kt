package com.example.data.store

import com.example.data.model.ProjectEntity
import com.example.data.repository.ProjectsCache
import com.example.data.repository.ProjectsDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

public class ProjectsCacheDataStore @Inject constructor(
    private val cacheDataStore: ProjectsCache
) : ProjectsDataStore{

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return cacheDataStore.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return cacheDataStore.saveProjects(projects).andThen(cacheDataStore.setLastCached(System.currentTimeMillis()))    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return cacheDataStore.getBookmarkedProjects()
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return cacheDataStore.setProjectAsBookmarked(projectId)
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return cacheDataStore.setProjectAsNotBookmarked(projectId)
    }
}
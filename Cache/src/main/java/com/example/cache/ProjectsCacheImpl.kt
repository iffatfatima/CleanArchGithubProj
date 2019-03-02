package com.example.cache

import com.example.cache.db.ProjectsDatabase
import com.example.cache.mapper.CachedProjectMapper
import com.example.cache.model.Config
import com.example.data.model.ProjectEntity
import com.example.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val projectsDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper
): ProjectsCache {

    override fun clearProjects(): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().insertProjects(
                projects.map{mapper.mapToCached(it)}
            )
            Completable.complete()
        }
    }

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getProjects()
            .toObservable()
            .map {
                it.map {cachedProject -> mapper.mapFromCached(cachedProject) }
            }
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getBookmarkedProjects()
            .toObservable()
            .map {
                it.map {cachedProject -> mapper.mapFromCached(cachedProject) }
            }
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return Completable.defer {
            val result = projectsDatabase.cachedProjectsDao().setBookmarkStatus(true, projectId)
            if(result>0){ Completable.complete() }
            else{ Completable.error(Throwable("The project was not bookmarked"))}
        }
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return Completable.defer {
            val result = projectsDatabase.cachedProjectsDao().setBookmarkStatus(false, projectId)
            if(result>0){ Completable.complete() }
            else{ Completable.error(Throwable("The project was not un-bookmarked"))}
        }
    }

    override fun areProjectsCached(): Single<Boolean> {
        return projectsDatabase.cachedProjectsDao().getProjects().map{!it.isEmpty()}.map{!it}
    }

    override fun setLastCached(lastCache: Long): Completable {
        return Completable.defer{
            projectsDatabase.configDao().insertConfig(Config(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isProjectCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60*10*1000).toLong()
        return projectsDatabase.configDao().getConfig()
            .toSingle(Config(lastCacheTime = 0))
            .map {
                currentTime-it.lastCacheTime>expirationTime
            }
    }
}
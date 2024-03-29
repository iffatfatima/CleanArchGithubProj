package com.example.data.repository;

import com.example.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ProjectsCache{
    fun clearProjects(): Completable
    fun getProjects(): Observable<List<ProjectEntity>>
    fun saveProjects(projects: List<ProjectEntity>): Completable
    fun getBookmarkedProjects(): Observable<List<ProjectEntity>>
    fun setProjectAsBookmarked(projectId: String): Completable
    fun setProjectAsNotBookmarked(projectId: String): Completable
    fun areProjectsCached(): Single<Boolean>
    fun setLastCached(lastCacheTime: Long): Completable
    fun isProjectCacheExpired(): Single<Boolean>
}
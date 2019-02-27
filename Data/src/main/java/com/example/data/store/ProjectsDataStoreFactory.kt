package com.example.data.store

import com.example.data.repository.ProjectsDataStore
import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
    private val projectsRemoteDataStore: ProjectsRemoteDataStore,
    private val projectsCacheDataStore: ProjectsCacheDataStore
){
    open fun getDataStore(projectsCached: Boolean, cacheExpired: Boolean) : ProjectsDataStore{
        return if(projectsCached && !cacheExpired){
            projectsCacheDataStore
        } else{
            projectsRemoteDataStore
        }
    }

    open fun getCacheDataStore(): ProjectsDataStore {
        return projectsCacheDataStore
    }
}
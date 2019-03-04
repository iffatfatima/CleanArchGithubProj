package com.example.mobileui.injection.module

import android.app.Application
import com.example.cache.db.ProjectsDatabase
import com.example.data.repository.ProjectsCache
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    @Singleton
    fun providesDataBase(application: Application): ProjectsDatabase {
        return ProjectsDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    fun providesProjectsCache(application: Application): ProjectsCache {
        return mock<ProjectsCache>()
    }

}
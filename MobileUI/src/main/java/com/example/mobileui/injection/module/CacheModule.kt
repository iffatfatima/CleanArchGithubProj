package com.example.mobileui.injection.module

import android.app.Application
import com.example.cache.ProjectsCacheImpl
import com.example.cache.db.ProjectsDatabase
import com.example.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class CacheModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun providesDataBase(application: Application): ProjectsDatabase {
            return ProjectsDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindsProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache
}
package com.example.mobileui.injection.module

import android.app.Application
import com.example.data.repository.ProjectsRemote
import com.example.remote.service.GithubTrendingService
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {
    @Provides
    @JvmStatic
    fun providesGuthubService(application: Application): GithubTrendingService {
        return mock<GithubTrendingService>()
    }

    @Provides
    @JvmStatic
    fun providesProjectsRemote(application: Application): ProjectsRemote {
        return mock<ProjectsRemote>()
    }
}
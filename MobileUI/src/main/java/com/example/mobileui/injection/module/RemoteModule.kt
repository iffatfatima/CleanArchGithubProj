package com.example.mobileui.injection.module

import com.example.data.repository.ProjectsRemote
import com.example.remote.ProjectsRemoteImpl
import com.example.remote.service.GithubTrendingService
import com.example.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {
//    @Module
//    companion object {
//        @Provides
//        @JvmStatic
//        fun providesGithubService(): GithubTrendingService {
//            return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
//        }
//    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote
}
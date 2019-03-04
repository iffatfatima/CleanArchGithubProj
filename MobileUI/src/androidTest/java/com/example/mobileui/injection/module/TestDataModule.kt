package com.example.mobileui.injection.module

import android.app.Application
import com.example.domain.repository.ProjectsRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {
    @Singleton
    @Provides
    @JvmStatic
    fun providesDataBase(application: Application): ProjectsRepository {
        return mock<ProjectsRepository>()
    }
}
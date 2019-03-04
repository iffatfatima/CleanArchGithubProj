package com.example.mobileui.injection

import android.app.Application
import com.example.domain.repository.ProjectsRepository
import com.example.mobileui.injection.module.*
import com.example.mobileui.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class ,
    TestApplicationModule::class,
    UiModule::class,
    TestDataModule::class,
    TestCacheModule::class,
    TestRemoteModule::class,
    PresentationModule::class
    )
)
interface TestApplicationComponent {
    fun projectsRepository(): ProjectsRepository

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application):Builder
        fun build():TestApplicationComponent
    }

    fun inject(application:TestApplication)

}
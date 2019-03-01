package com.example.mobileui.injection

import android.app.Application
import com.example.mobileui.GithubTrendingApplication
import com.example.mobileui.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    ApplicationModule::class,
    UiModule::class,
    PresentationModule::class,
    DataModule::class,
    CacheModule::class,
    RemoteModule::class
    )
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application):Builder
        fun build():ApplicationComponent
    }

    fun inject(app: GithubTrendingApplication)
}
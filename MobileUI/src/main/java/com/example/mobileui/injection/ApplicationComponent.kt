package com.example.mobileui.injection

import android.app.Application
import com.example.mobileui.GithubTrendingApplication
import com.example.mobileui.injection.module.ApplicationModule
import com.example.mobileui.injection.module.PresentationModule
import com.example.mobileui.injection.module.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, ApplicationModule::class, UiModule::class, PresentationModule::class))
interface ApplicationComponent{
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application:Application):Builder

        fun build(): ApplicationComponent
    }

    fun inject(app:GithubTrendingApplication)
}
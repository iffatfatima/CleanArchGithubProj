package com.example.mobileui.injection.module

import com.example.domain.executor.PostExecutionThread
import com.example.mobileui.browse.BrowseActivity
import com.example.mobileui.UiThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule{

    @Binds
    abstract fun bindPostExecututionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

}
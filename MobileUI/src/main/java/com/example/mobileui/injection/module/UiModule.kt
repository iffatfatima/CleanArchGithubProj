package com.example.mobileui.injection.module

import com.example.domain.executor.PostExecutionThread
import com.example.mobileui.browse.BrowseActivity
import com.example.mobileui.UiThread
import com.example.mobileui.bookmarked.BookmarkedActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule{

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributesBookmarkedActivity(): BookmarkedActivity


}
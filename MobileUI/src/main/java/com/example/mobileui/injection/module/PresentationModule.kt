package com.example.mobileui.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.mobileui.injection.ViewModelFactory
import com.example.presentation.BrowseProjectViewModel
import com.example.presentation.state.BrowseBookmarkedViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseProjectViewModel::class)
    abstract fun bindBrowseProjectViewModel(viewModel: BrowseProjectViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrowseBookmarkedViewModel::class)
    abstract fun bindBrowseBookmarkedViewModel(viewModel: BrowseBookmarkedViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
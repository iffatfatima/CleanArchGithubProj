package com.example.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.domain.bookmark.BookmarkProject
import com.example.domain.interactor.browse.GetProjects
import com.example.domain.model.Project
import com.example.domain.unbookmark.UnbookmarkProject
import com.example.presentation.mapper.ProjectViewMapper
import com.example.presentation.model.ProjectView
import com.example.presentation.state.Resource
import com.example.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

public class BrowseProjectViewModel @Inject constructor(
    private val getProjects: GetProjects,
    private val bookmarkProject: BookmarkProject,
    private val unbookmarkProject: UnbookmarkProject,
    private val mapper: ProjectViewMapper
): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared(){
        getProjects.dispose()
        super.onCleared()
    }

    fun getProjects(): MutableLiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects(){
        liveData.postValue(Resource(ResourceState.LOADING, null,null))
        return getProjects.execute(ProjectsSubscriber(), null)
    }

    fun bookmarkProject(projectId: String){
        return bookmarkProject.execute(BookmarkProjectsSubscriber(), BookmarkProject.Params.forProject(projectId))
    }

    fun unbookmarkProject(projectId: String){
        return unbookmarkProject.execute(BookmarkProjectsSubscriber(), UnbookmarkProject.Params.forProject(projectId))
    }

    inner class ProjectsSubscriber: DisposableObserver<List<Project>>() {
        override fun onComplete() {
        }

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, t.map{mapper.mapToView(it)}, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

    inner class BookmarkProjectsSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }
    inner class UnBookmarkProjectsSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, liveData.value?.data, e.localizedMessage))
        }
    }
}
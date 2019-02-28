package com.example.presentation.state

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.domain.bookmarked.GetBookmarkedProjects
import com.example.domain.model.Project
import com.example.presentation.mapper.ProjectViewMapper
import com.example.presentation.model.ProjectView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookmarkedProjects,
    private val mapper: ProjectViewMapper
): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getBookmarkedProjects.execute(ProjectsSubscriber(), null)
    }

    inner class ProjectsSubscriber: DisposableObserver<List<Project>>(){

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, t.map { project -> mapper.mapToView(project) }, null))
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

}
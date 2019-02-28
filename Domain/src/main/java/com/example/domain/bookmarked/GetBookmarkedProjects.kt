package com.example.domain.bookmarked

import com.example.domain.executor.PostExecutionThread
import com.example.domain.interactor.ObservableUseCase
import com.example.domain.model.Project
import com.example.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetBookmarkedProjects @Inject constructor(
    val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread

) : ObservableUseCase<List<Project>, Nothing?>(postExecutionThread) {
    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getBookmarkedProjects()
    }

}
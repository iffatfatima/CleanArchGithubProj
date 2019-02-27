package com.example.domain.bookmark

import com.example.domain.executor.PostExecutionThread
import com.example.domain.interactor.CompletableUseCase
import com.example.domain.model.Project
import com.sun.xml.internal.ws.util.CompletedFuture
import com.teamo.clean.domain.repository.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

open class BookmarkProject @Inject constructor(
    val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
): CompletableUseCase<BookmarkProject.Params>(postExecutionThread){
    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if(params == null){
            throw IllegalArgumentException("Params can not be null")
        }
        return projectsRepository.bookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String){
        companion object {
            fun forProject(projectId: String): Params{
                return Params(projectId)
            }
        }
    }
}
package com.example.domain.unbookmark

import com.example.domain.executor.PostExecutionThread
import com.example.domain.interactor.CompletableUseCase
import com.teamo.clean.domain.repository.ProjectsRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class UnbookmarkProject @Inject constructor(
    val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
): CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {
    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if(params == null){
            throw IllegalArgumentException("Params can not be null")
        }
        return projectsRepository.unbookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params{
                return Params(projectId)
            }
        }
    }

}
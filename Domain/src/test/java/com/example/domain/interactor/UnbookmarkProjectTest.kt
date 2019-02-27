package com.example.domain.interactor

import com.example.domain.unbookmark.UnbookmarkProject
import com.example.domain.executor.PostExecutionThread
import com.example.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.teamo.clean.domain.repository.ProjectsRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnunbookmarkProjectTest{

    private lateinit var unbookmarkProjects: UnbookmarkProject
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        unbookmarkProjects = UnbookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun unbookmarkProjectCompletes(){
        stubUnbookmarkProject(Completable.complete())
        val testObserver = unbookmarkProjects.buildUseCaseCompletable(
            UnbookmarkProject.Params.forPorjects(
                ProjectDataFactory.randomUuid())).test()
        testObserver.assertComplete()
    }

    @Test(expected =  IllegalArgumentException::class)
    fun unbookmarkProjectThrowsException(){
        unbookmarkProjects.buildUseCaseCompletable().test()
    }

    private fun stubUnbookmarkProject(completable: Completable){
        whenever(projectsRepository.unbookmarkProject(any()))
            .thenReturn(completable)
    }
}
package com.example.domain.interactor

import com.example.domain.executor.PostExecutionThread
import com.example.domain.interactor.browse.GetProjects
import com.example.domain.model.Project
import com.example.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.whenever
import com.teamo.clean.domain.repository.ProjectsRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest{

    private lateinit var getProjects: GetProjects
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getProjectsCompletes(){
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))//creates a list of project to be returned by getProjects
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData(){
        val projects = ProjectDataFactory.makeProjectList(2)//creates a list of project to be returned by getProjects
        stubGetProjects(Observable.just(projects))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)//we check that the list returned is actually the one we created above for return
    }

    private fun stubGetProjects(observable:Observable<List<Project>>){
        whenever(projectsRepository.getProjects())
            .thenReturn(observable)
    }
}
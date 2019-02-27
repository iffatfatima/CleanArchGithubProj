package com.example.domain.interactor

import com.example.domain.bookmarked.GetBookmarkedProjects
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

class GetBookmarkedProjectsTest {

    private lateinit var getBookmarkedProjects: GetBookmarkedProjects
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkedProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getProjectsCompletes(){
        stubGetBookmarkedProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))//creates a list of project to be returned by getProjects
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData(){
        val projects = ProjectDataFactory.makeProjectList(2)//creates a list of project to be returned by getProjects
        stubGetBookmarkedProjects(Observable.just(projects))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)//we check that the list returned is actually the one we created above for return
    }

    private fun stubGetBookmarkedProjects(observable:Observable<List<Project>>){
        whenever(projectsRepository.getBookmarkedProjects())
            .thenReturn(observable)
    }

}
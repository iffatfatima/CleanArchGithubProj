package com.example.domain.interactor

import com.example.domain.bookmark.BookmarkProject
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

class BookmarkProjectTest {

    private lateinit var bookmarkProjects: BookmarkProject
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkProjects = BookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes(){
        stubBookmarkProject(Completable.complete())
        val testObserver = bookmarkProjects.buildUseCaseCompletable(BookmarkProject.Params.forPorjects(ProjectDataFactory.randomUuid())).test()
        testObserver.assertComplete()
    }

    @Test(expected =  IllegalArgumentException::class)
    fun bookmarkProjectThrowsException(){
        bookmarkProjects.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkProject(completable: Completable){
        whenever(projectsRepository.bookmarkProject(any()))
            .thenReturn(completable)
    }

}
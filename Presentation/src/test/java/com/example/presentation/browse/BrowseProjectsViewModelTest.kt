package com.example.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.bookmark.BookmarkProject
import com.nhaarman.mockitokotlin2.*

import com.example.domain.interactor.browse.GetProjects
import com.example.domain.model.Project
import com.example.domain.unbookmark.UnbookmarkProject
import com.example.presentation.BrowseProjectViewModel
import com.example.presentation.mapper.ProjectViewMapper
import com.example.presentation.model.ProjectView
import com.example.presentation.state.ResourceState
import com.example.presentation.test.factory.DataFactory
import com.example.presentation.test.factory.ProjectFactory
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import java.lang.RuntimeException

@RunWith(JUnit4::class)
class BrowseProjectViewModelTest {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var getProjects = mock<GetProjects>()
    var bookmarkProject = mock<BookmarkProject>()
    var unbookmarkProject = mock<UnbookmarkProject>()
    var projectMapper = mock<ProjectViewMapper>()
    var projectViewModel = BrowseProjectViewModel(getProjects, bookmarkProject, unbookmarkProject, projectMapper)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecuteUseCase(){
        projectViewModel.fetchProjects()
        verify(getProjects, times(1)).execute(any(), eq(null))//verifies that getProjects's execute function is called one time when projects are fetched
    }

    @Test
    fun fetchProjectsReturnSuccess(){
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projectViews[0], projects[0])
        stubProjectMapperMapToView(projectViews[0], projects[1])
        projectViewModel.fetchProjects()
        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(ResourceState.SUCCESS,
            projectViewModel.getProjects().value?.status)

    }

    @Test
    fun fetchProjectsReturnError(){
        projectViewModel.fetchProjects()
        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        assertEquals(ResourceState.ERROR,
            projectViewModel.getProjects().value?.status)

    }

    @Test
    fun fetchProjectsReturnMessageForError(){
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()
        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(errorMessage,
            projectViewModel.getProjects().value?.message)

    }

    @Test
    fun fetchProjectsReturnData(){
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projectViews[0], projects[0])
        stubProjectMapperMapToView(projectViews[1], projects[1])

        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        Assert.assertEquals(projectViews, projectViewModel.getProjects().value?.data)
    }

    fun stubProjectMapperMapToView(projectView: ProjectView, project: Project){
        whenever(projectMapper.mapToView(project)).thenReturn(projectView)

    }
}
package com.example.remote

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.example.data.model.ProjectEntity
import com.example.remote.mapper.ProjectResponseModelMapper
import com.example.remote.model.ProjectModel
import com.example.remote.model.ProjectsResponseModel
import com.example.remote.service.GithubTrendingService
import com.example.remote.test.factory.ProjectDataFactory
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteImplTest {
    val service = mock< GithubTrendingService>()
    val mapper = mock<ProjectResponseModelMapper>()
    private val remoteService = ProjectsRemoteImpl(service, mapper)

    @Test
    fun getProjectsCompletes(){
        val model = ProjectDataFactory.makeProject()
        val entity = ProjectDataFactory.makeProjectEntity()
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        stubMapFromModel(model, entity)
        val testObserver = remoteService.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnData(){
        val response = ProjectDataFactory.makeProjectsResponse()
        stubGetProjects(Observable.just(response))
        val entities = mutableListOf<ProjectEntity>()
        response.projectItems.forEach {
            val entity = ProjectDataFactory.makeProjectEntity()
            entities.add(entity)
            stubMapFromModel(it, entity)
        }
        val testObserver = remoteService.getProjects().test()
        testObserver.assertValue(entities)
    }

    @Test
    fun getProjectsCallsServerWithCorrectParams(){
        val model = ProjectDataFactory.makeProject()
        val entity = ProjectDataFactory.makeProjectEntity()
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        stubMapFromModel(model, entity)
        remoteService.getProjects().test()
        verify(service).searchRepositories("language:kotlin", "stars", "desc")
    }

    @Test
    fun getProjectsCallsServer(){
        val model = ProjectDataFactory.makeProject()
        val entity = ProjectDataFactory.makeProjectEntity()
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        stubMapFromModel(model, entity)
        remoteService.getProjects().test()
        verify(service).searchRepositories(any(), any(), any())
    }

    private fun stubGetProjects(observable: Observable<ProjectsResponseModel>){
        whenever(service.searchRepositories(any(), any(), any()))
            .thenReturn(observable)
    }

    private fun stubMapFromModel(model: ProjectModel, entity: ProjectEntity){
        whenever(mapper.mapFromModel(model))
            .thenReturn(entity)
    }
}
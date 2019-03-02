package com.example.data.store

import com.example.data.model.ProjectEntity
import com.example.data.repository.ProjectsDataStore
import com.example.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

public class ProjectsRemoteDataStore @Inject constructor(
    private val  projectsRemote: ProjectsRemote
): ProjectsDataStore{
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn't supported")
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Fetching bookmarked projects isn't supported")    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Bookmarking projects isn't supported")    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Un-bookmarking projects isn't supported")    }

}
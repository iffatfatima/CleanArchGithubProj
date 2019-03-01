package com.example.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.nhaarman.mockitokotlin2.mock
import com.example.cache.db.ProjectsDatabase
import com.example.cache.mapper.CacheMapper
import com.example.cache.mapper.CachedProjectMapper
import com.example.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ProjectsCacheImplTest {

    @get:Rule var instanttaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    val mapper = CachedProjectMapper()
    val cache = ProjectsCacheImpl(database, mapper)

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun clearTableCompletes(){
        val testObserver = cache.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveProjectsCompletes(){
        val projects = listOf(ProjectDataFactory.makeProjectEntity())

        val testObserver = cache.saveProjects(projects).test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData(){
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val testObserver = cache.getProjects().test()
        testObserver.assertValue(projects)
    }

    @Test
    fun getBookmarkedProjectsReturnsData(){
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedProjectEntity()
        val project = ProjectDataFactory.makeProjectEntity()
        cache.saveProjects(listOf(project, bookmarkedProject)).test()

        val testObserver = cache.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setProjectBookmarkedCompletes(){
        val project = ProjectDataFactory.makeBookmarkedProjectEntity()
        cache.saveProjects(listOf(project)).test()

        val testObserver = cache.setProjectAsBookmarked(project.id).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectNotBookmarkedCompletes(){
        val project = ProjectDataFactory.makeProjectEntity()
        cache.saveProjects(listOf(project)).test()

        val testObserver = cache.setProjectAsNotBookmarked(project.id).test()
        testObserver.assertComplete()
    }

    @Test
    fun areProjectsCacheReturnsData(){
        val project = ProjectDataFactory.makeProjectEntity()
        cache.saveProjects(listOf(project)).test()

        val testObserver = cache.areProjectsCached().test()
        testObserver.assertValue(true)
    }

    @Test
    fun setLastCacheTimeCompletes(){
        val testObserver = cache.setLastCacheTime(1000).test()
        testObserver.assertComplete()
    }

    @Test
    fun isProjectsCacheExpiredNoValueReturnsExpired(){
        val testObserver = cache.isProjectsCacheExpired().test()
        testObserver.assertValue(true)
    }

    @Test
    fun isProjectsCacheExpiredReturnsExpired(){
        cache.setLastCacheTime(1000).test()
        val testObserver = cache.isProjectsCacheExpired().test()
        testObserver.assertValue(true)
    }

    @Test
    fun isProjectsCacheNotExpiredReturnsExpired(){
        cache.setLastCacheTime(System.currentTimeMillis()-1000).test()
        val testObserver = cache.isProjectsCacheExpired().test()
        testObserver.assertValue(false)
    }

}
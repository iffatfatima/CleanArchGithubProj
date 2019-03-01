package com.example.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.example.cache.db.ProjectsDatabase
import com.example.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class CahcedProjectsDaoTest{

    @Rule
    @JvmField var instanttaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun getProjectsReturnsData(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))

        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(listOf(project))
    }


    @Test
    fun deleteProjectsTest(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))

        database.cachedProjectsDao().deleteProjects()

        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectsReturnsData(){
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeCachedBookmarkedProject()

        database.cachedProjectsDao().insertProjects(listOf(project, bookmarkedProject))

        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setBookmarkStatusTrueReturnsData(){
        val project = ProjectDataFactory.makeCachedProject()
        val projectId = project.id

        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(true, projectId)

        project.isBookmarked = true

        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun setBookmarkStatusFalseReturnsData(){
        val project = ProjectDataFactory.makeCachedBookmarkedProject()
        val projectId = project.id

        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(false, projectId)

        project.isBookmarked = false

        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(emptyList())
    }
}
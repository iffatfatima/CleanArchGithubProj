package com.example.mobileui.browse

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.domain.model.Project
import com.example.mobileui.R
import com.example.mobileui.test.TestApplication
import com.example.mobileui.test.factory.TestProjectFactory
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BrowseProjectActivityTest {

    @Rule @JvmField
    val activity = ActivityTestRule<BrowseActivity>(BrowseActivity::class.java,false,false)

    @Test
    fun activityLaunches(){

            stubProjectsRepositoryGetProjects(Observable.just(listOf(TestProjectFactory.makeProject())))
            activity.launchActivity(null)

    }

    @Test
    fun projectsDisplay(){
            val projects = listOf(TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),TestProjectFactory.makeProject())
            stubProjectsRepositoryGetProjects(Observable.just(projects))
            activity.launchActivity(null)
            projects.forEachIndexed { index, project ->
                onView(withId(R.id.recycler_projects)).perform(RecyclerViewActions.scrollToPosition<BrowseAdapter.ViewHolder>(index))
                onView(withId(R.id.recycler_projects)).check(matches(hasDescendant(withText(project.fullName))))
            }

    }

    private fun stubProjectsRepositoryGetProjects(observable: Observable<List<Project>>){
        whenever(TestApplication.appComponent().projectsRepository().getProjects())
            .thenReturn(observable)
    }

}


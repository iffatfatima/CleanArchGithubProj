package com.example.presentation.mapper

import com.example.presentation.test.factory.ProjectFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectViewMapperTest {

    private val mapper = ProjectViewMapper()

    @Test
    fun mapToViewMapsData(){
        val project = ProjectFactory.makeProject()
        val projectView = mapper.mapToView(project)

        Assert.assertEquals(project.id, projectView.id)
        Assert.assertEquals(project.name, projectView.name)
        Assert.assertEquals(project.fullName, projectView.fullName)
        Assert.assertEquals(project.dateCreated, projectView.dateCreated)
        Assert.assertEquals(project.starCount, projectView.starCount)
        Assert.assertEquals(project.ownerName, projectView.ownerName)
        Assert.assertEquals(project.ownerAvatar, projectView.ownerAvatar)
        Assert.assertEquals(project.isBookmarked, projectView.isBookmarked)
    }

}
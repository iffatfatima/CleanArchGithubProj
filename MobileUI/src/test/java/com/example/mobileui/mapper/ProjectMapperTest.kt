package com.example.mobileui.mapper

import com.example.mobileui.test.factory.ProjectFactory
import org.junit.Test
import kotlin.test.assertEquals

class ProjectMapperTest{
    private val projectMapper:ProjectViewMapper = ProjectViewMapper()

    @Test
    fun mapToViewMapsData(){
        val project = ProjectFactory.makeProject()
        val projectView = ProjectFactory.makeProjectView()

        assertEquals(project.id, projectView.id)
        assertEquals(project.name, projectView.name)
        assertEquals(project.fullName, projectView.fullName)
        assertEquals(project.dateCreated, projectView.dateCreated)
        assertEquals(project.ownerAvatar, projectView.ownerAvatar)
        assertEquals(project.ownerName, projectView.ownerName)
        assertEquals(project.isBookmarked, projectView.isBookmarked)

    }
}
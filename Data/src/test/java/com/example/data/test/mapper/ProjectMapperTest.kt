package com.example.data.test.mapper

import com.example.data.mapper.ProjectMapper
import com.example.data.model.ProjectEntity
import com.example.data.test.ProjectFactory
import com.example.domain.model.Project
import org.junit.Test
import kotlin.test.assertEquals

class ProjectMapperTest{

    private val mapper = ProjectMapper()

    @Test
    fun mapFromEntityMapData(){
        val entity = ProjectFactory.makeProjectEntity()
        val model = mapper.mapFromEntity(entity)
        assertEqualData(entity, model)
    }

    @Test
    fun mapToEntityMapData(){
        val model = ProjectFactory.makeProject()
        val entity = mapper.mapToEntity(model)
        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: ProjectEntity, model: Project){
        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.fullName, model.fullName)
        assertEquals(entity.starCount, model.starCount)
        assertEquals(entity.dateCreated, model.dateCreated)
        assertEquals(entity.ownerName, model.ownerName)
        assertEquals(entity.ownerAvatar, model.ownerAvatar)
        assertEquals(entity.isBookmarked, model.isBookmarked)
    }
}

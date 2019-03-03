package com.example.remote.test.factory

import com.example.data.model.ProjectEntity
import com.example.remote.model.OwnerModel
import com.example.remote.model.ProjectModel
import com.example.remote.model.ProjectsResponseModel

object ProjectDataFactory {
    fun makeOwner(): OwnerModel {
        return OwnerModel(DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeProject(): ProjectModel {
        return ProjectModel(
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(), makeOwner()
        )
    }

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity( DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomBoolean()
        )
    }

    fun makeProjectsResponse(): ProjectsResponseModel{
        return ProjectsResponseModel(listOf(makeProject()))
    }
}
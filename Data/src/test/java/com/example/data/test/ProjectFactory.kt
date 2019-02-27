package com.example.data.test

import com.example.data.model.ProjectEntity
import com.example.data.test.factory.DataFactory
import com.example.domain.model.Project

object ProjectFactory{

    fun makeProjectEntity(): ProjectEntity{
        return ProjectEntity(DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(),DataFactory.randomBoolean())
    }

    fun makeProject(): Project {
        return Project(DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(),DataFactory.randomBoolean())
    }

}
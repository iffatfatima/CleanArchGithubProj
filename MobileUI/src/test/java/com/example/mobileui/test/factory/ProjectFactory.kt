package com.example.mobileui.test.factory

import com.example.domain.model.Project
import com.example.presentation.model.ProjectView

object ProjectFactory {

    fun makeProjectView(): ProjectView {
        return ProjectView(DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(),DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomBoolean()
        )
    }

    fun makeProject(): Project {
        return Project(DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(),DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomBoolean()
        )
    }
}
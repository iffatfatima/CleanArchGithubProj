package com.example.mobileui.test.factory

import com.example.domain.model.Project
import com.example.presentation.model.ProjectView

object TestProjectFactory {

    fun makeProjectView(): ProjectView {
        return ProjectView(ProjectDataFactory.randomString(),
            ProjectDataFactory.randomString(), ProjectDataFactory.randomString(),
            ProjectDataFactory.randomString(),ProjectDataFactory.randomString(),
            ProjectDataFactory.randomString(), ProjectDataFactory.randomBoolean()
        )
    }

    fun makeProject(): Project {
        return Project(ProjectDataFactory.randomString(),
            ProjectDataFactory.randomString(), ProjectDataFactory.randomString(),
            ProjectDataFactory.randomString(),ProjectDataFactory.randomString(),
            ProjectDataFactory.randomString(), ProjectDataFactory.randomBoolean()
        )
    }
}
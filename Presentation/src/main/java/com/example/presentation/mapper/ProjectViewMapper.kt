package com.example.presentation.mapper

import com.example.domain.model.Project
import com.example.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : Mapper<ProjectView, Project>{
    override fun mapToView(type: Project): ProjectView {
        return ProjectView(type.id, type.name, type.fullName,
        type.dateCreated,
        type.ownerName, type.ownerAvatar,
        type.isBookmarked)
    }
}
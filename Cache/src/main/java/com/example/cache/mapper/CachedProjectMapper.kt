package com.example.cache.mapper

import com.example.cache.model.CachedProject
import com.example.data.model.ProjectEntity
import javax.inject.Inject

class CachedProjectMapper @Inject constructor():CacheMapper<CachedProject, ProjectEntity> {
    override fun mapFromCached(cachedProject: CachedProject): ProjectEntity {
        return ProjectEntity(cachedProject.id, cachedProject.name, cachedProject.fullName, cachedProject.starCount, cachedProject.dateCreated,
            cachedProject.ownerName, cachedProject.ownerAvatar, cachedProject.isBookmarked)
    }

    override fun mapToCached(projectEntity: ProjectEntity): CachedProject {
        return CachedProject(projectEntity.id, projectEntity.name, projectEntity.fullName, projectEntity.starCount, projectEntity.dateCreated,
            projectEntity.ownerName, projectEntity.ownerAvatar, projectEntity.isBookmarked)
    }
}
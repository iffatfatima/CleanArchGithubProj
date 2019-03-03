package com.example.presentation.model

data class ProjectView(val id: String, val name: String, val fullName: String,
                       val dateCreated: String,
                       val ownerName: String, val ownerAvatar: String,
                       var isBookmarked: Boolean)
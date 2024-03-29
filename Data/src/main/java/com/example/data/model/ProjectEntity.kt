package com.example.data.model

data class ProjectEntity(val id:String, val name: String, val fullName: String, val dateCreated:String,
                    val ownerName: String, val ownerAvatar:String,
                    val isBookmarked: Boolean)
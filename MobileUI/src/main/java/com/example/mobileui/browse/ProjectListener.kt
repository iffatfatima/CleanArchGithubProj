package com.example.mobileui.browse

interface ProjectListener {

    fun onBookmarkedProjectClicked(projectId: String)
    fun onProjectClicked(projectId: String)
}
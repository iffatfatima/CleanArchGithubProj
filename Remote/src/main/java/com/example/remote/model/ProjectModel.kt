package com.example.remote.model

import com.google.gson.annotations.SerializedName

public class ProjectModel (
    val id: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("created_at") val dateCreated: String,
    val owner: OwnerModel
)
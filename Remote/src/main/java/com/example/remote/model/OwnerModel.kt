package com.example.remote.model

import com.google.gson.annotations.SerializedName

public class OwnerModel(@SerializedName("login") val ownerName: String,
                 @SerializedName("avatar_url") val ownerAvatar: String)
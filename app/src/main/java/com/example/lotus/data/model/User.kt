package com.example.lotus.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("conformPassword")
    val conformPassword: String,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("facebook")
    val facebook: String? = null,

    @SerializedName("linkedin")
    val linkedin: String? = null,

    @SerializedName("github")
    val github: String? = null,

    @SerializedName("twitter")
    val twitter: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("backgroundImage")
    val backgroundImage: String? = null,

    @SerializedName("friendRequests")
    val friendRequests: List<String> = emptyList(),

    @SerializedName("friends")
    val friends: List<String> = emptyList(),

    @SerializedName("createdOn")
    val createdOn: String
)


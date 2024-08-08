package com.example.lotus.data.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("content")
    val content: String,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("user")
    val user: String,
    @SerializedName("likes")
    val likes: List<User> = emptyList(),
    @SerializedName("comments")
    val comments: List<Comment> = emptyList(),
    @SerializedName("createdOn")
    val createdOn: String
)
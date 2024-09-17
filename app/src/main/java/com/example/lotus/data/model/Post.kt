package com.example.lotus.data.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("_id")
    val id: String,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("user")
    val user: User,
    @SerializedName("likes")
    val likes: List<String> = emptyList(),
    @SerializedName("comments")
    val comments: List<Comment> = emptyList(),
    @SerializedName("createdOn")
    val createdOn: String,
)

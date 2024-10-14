package com.example.lotus.data.model

import com.google.gson.annotations.SerializedName

data class PostUserString (
    @SerializedName("_id")
    val id: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("user")
    val user: String,
    @SerializedName("likes")
    val likes: List<String> = emptyList(),
    @SerializedName("comments")
    val comments: List<Comment> = emptyList(),
    @SerializedName("createdOn")
    val createdOn: String
)
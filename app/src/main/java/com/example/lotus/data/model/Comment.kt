package com.example.lotus.data.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("content")
    val content: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("createdOn")
    val createdOn: String
)
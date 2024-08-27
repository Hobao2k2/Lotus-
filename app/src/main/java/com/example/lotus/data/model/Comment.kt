package com.example.lotus.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    @SerializedName("content")
    val content: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("createdOn")
    val createdOn: String
):Parcelable
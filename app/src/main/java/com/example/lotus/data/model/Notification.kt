package com.example.lotus.data.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("notificationRecever")
    val notificationRecever: String,
    @SerializedName("notificationSender")
    val notificationSender: User,
    @SerializedName("message")
    val message: String,
    @SerializedName("isRead")
    val isRead: Boolean = false,
    @SerializedName("createdAt")
    val createdAt: String
)


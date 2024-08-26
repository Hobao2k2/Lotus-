package com.example.lotus.ui.adapter.dataItem

import com.example.lotus.data.model.Comment
import com.example.lotus.data.model.User
import com.google.gson.annotations.SerializedName

data class Item2(
    val imageAvatar: String?,
    val imagePost: String?,
    val name: String,
    val content: String,
    val id: String,
    val likes:List<String>,
    val comments:List<Comment>

)
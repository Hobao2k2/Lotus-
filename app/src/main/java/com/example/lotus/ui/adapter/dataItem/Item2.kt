package com.example.lotus.ui.adapter.dataItem

import com.example.lotus.data.model.Comment
import com.example.lotus.data.model.User

data class Item2(
    val imageResId: Int?,
    val imageAvatar: String?,
    val imagePost: String?,
    val name: String,
    val content: String,
    val id: String,
    val likes:List<User>,
    val comments:List<Comment>
)
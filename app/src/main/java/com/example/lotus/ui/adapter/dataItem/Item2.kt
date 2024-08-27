package com.example.lotus.ui.adapter.dataItem

import android.os.Parcelable
import com.example.lotus.data.model.Comment
import com.example.lotus.data.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item2(
    val imageAvatar: String?,
    val imagePost: String?,
    val name: String,
    val content: String,
    val id: String,
    val likes: List<String>,
    val comments: List<Comment>
) : Parcelable, BaseItem {
    override fun getType(): Int {
        return 1
    }
}

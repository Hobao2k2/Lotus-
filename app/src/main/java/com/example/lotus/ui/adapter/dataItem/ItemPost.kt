package com.example.lotus.ui.adapter.dataItem

import com.example.lotus.data.model.Comment
import com.example.lotus.data.model.PostDetailResponse
import com.example.lotus.data.model.PostListResponse
import com.example.lotus.data.model.User

data class ItemPost(
    val id: String,
    val content: String?,
    val userId: String,
    val name: String,
    val imageAvatar: String?,
    val imagePost: String?,
    val likes:List<String>,
    val comments:List<Comment>
) : ListItem{
    override fun getType(): ListItem.Type {
        return ListItem.Type.POST
    }
}
fun PostListResponse.toPost(): ItemPost {
    return ItemPost(
        id = id,
        content = content,
        userId = user.id,
        name = user.userName,
        imageAvatar = user.image,
        imagePost = image,
        likes = likes,
        comments = comments
    )
}

fun PostDetailResponse.toPost(user: User? = null): ItemPost {
    return ItemPost(
        id = id,
        content = content,
        userId = userId,
        name = user?.userName ?: "",
        imageAvatar = user?.image ?: "",
        imagePost = image,
        likes = likes,
        comments = comments
    )
}

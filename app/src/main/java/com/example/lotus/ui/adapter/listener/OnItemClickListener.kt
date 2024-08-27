package com.example.lotus.ui.adapter.listener

interface OnItemClickListener {
    fun onUpdateProfileClick()
    fun onAddAvatarClick()
    fun onPostClick()
    fun onItemClick(position: Int)
    fun onLikeClick(id: String)
    fun onCommentClick(id: String)
}
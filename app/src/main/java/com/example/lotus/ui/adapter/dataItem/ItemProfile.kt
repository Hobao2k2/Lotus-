package com.example.lotus.ui.adapter.dataItem

data class ItemProfile(
    val imageUrl: String?,
    val userName: String
) : ListItem{
    override fun getType(): ListItem.Type {
        return ListItem.Type.PROFILE
    }
}
package com.example.lotus.ui.adapter.dataItem

data class Item1(
    val imageUrl: String?,
    val userName: String
) : BaseItem {
    override fun getType(): Int {
        return 0
    }
}
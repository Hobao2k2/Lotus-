package com.example.lotus.ui.adapter.dataItem

interface ListItem {
    enum class Type(val value: Int) {
        PROFILE(0),
        POST(1)
    }

    fun getType(): Type
}

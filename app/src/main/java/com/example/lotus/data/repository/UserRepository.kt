package com.example.lotus.data.repository

import com.example.lotus.data.model.Post
import com.example.lotus.data.model.User
import com.example.lotus.data.network.RetrofitClient

class UserRepository {
    private val api=RetrofitClient.getRetrofitClient()

    suspend fun getUserProfile():User{
        return api.getUserProfile()
    }
    suspend fun getAllPost():List<Post>{
        return api.getAllPost()
    }
}
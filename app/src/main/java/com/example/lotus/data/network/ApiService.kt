package com.example.lotus.data.network

import com.example.lotus.data.model.Post
import com.example.lotus.data.model.User
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("/users/profile")
    suspend fun getUserProfile(): User
    @GET("/post/all")
    suspend fun getAllPost(
        @Header("Authorization") token: String
    ): List<Post>

}
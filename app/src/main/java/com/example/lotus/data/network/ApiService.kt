package com.example.lotus.data.network

import com.example.lotus.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("/users/profile")
    suspend fun getUserProfile(): User
}
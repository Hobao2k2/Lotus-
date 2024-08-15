package com.example.lotus.data.network

import android.media.session.MediaSession.Token
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import retrofit2.http.*
import okhttp3.MultipartBody
import retrofit2.Response

interface ApiService {

    @POST("/users")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("/users/login")
    suspend fun login(
        @Body registerRequest: RegisterRequest
    ): Response<String>

    @GET("/users/profile")
    suspend fun getUserProfile(): User

}
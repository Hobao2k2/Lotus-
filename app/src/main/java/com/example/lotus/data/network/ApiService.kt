package com.example.lotus.data.network

import android.media.session.MediaSession.Token
import com.example.lotus.data.model.Post
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface ApiService {

    @POST("users")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("/users/login")
    suspend fun login(
        @Body registerRequest: RegisterRequest
    ): Response<String>

    @GET("/users/profile")
    suspend fun getUserProfile(): User

    @Multipart
    @POST("/post")
    suspend fun addPost(
        @Part("content") content: RequestBody,
        @Part image:MultipartBody.Part?
    ):Post

    @GET("/post")
    suspend fun getAllPost():List<Post>

    @POST("/users/search/{keyword}")
    suspend fun getSearchUser(@Path("keyword") keyword: String): List<User>

    @GET("/users")
    suspend fun getAllUsers():List<User>

}
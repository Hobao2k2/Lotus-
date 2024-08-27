package com.example.lotus.data.network

import com.example.lotus.data.model.ApiResponse
import com.example.lotus.data.model.FriendId
import com.example.lotus.data.model.IdRequest
import com.example.lotus.data.model.PostUser
import com.example.lotus.data.model.PostUserId
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("/post")
    suspend fun addPost(
        @Part("content") content: RequestBody,
        @Part image:MultipartBody.Part?
    ):PostUserId

    @GET("/post")
    suspend fun getAllPost():List<PostUserId>

    @GET("/post/all")
    suspend fun getPosts():List<PostUser>

    @POST("/post/like/{idPost}")
    suspend fun likePost(@Path("idPost") idPost:String):PostUserId

    @POST("/users/search/{keyword}")
    suspend fun getSearchUser(@Path("keyword") keyword: String): List<User>

    @GET("/users")
    suspend fun getAllUsers():List<User>
    @GET("/users/{id}")
    suspend fun getUsersById(@Path("id")id:String):User

    @POST("/friends/send-request")
    suspend fun addFriend(@Body idRequest: IdRequest)
    @GET("/users/profile")
    suspend fun getUserProfile(): User

    @POST("/friends/unfriend")
    suspend fun unFriend(@Body friendId: FriendId)

}
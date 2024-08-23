package com.example.lotus.data.network

import com.example.lotus.data.model.FriendId
import com.example.lotus.data.model.Notification
import com.example.lotus.data.model.receiverId
import com.example.lotus.data.model.Post
import com.example.lotus.data.model.PostUserString
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
    ):Post

    @GET("/post")
    suspend fun getAllPost():List<Post>

    @POST("/users/search/{keyword}")
    suspend fun getSearchUser(@Path("keyword") keyword: String): List<User>

    @GET("/users")
    suspend fun getAllUsers():List<User>
    @GET("/users/{id}")
    suspend fun getUsersById(@Path("id")id:String):User

    @POST("/friends/send-request")
    suspend fun addFriend(@Body idRequest: receiverId)
    @GET("/users/profile")
    suspend fun getUserProfile(): User

    @POST("/friends/unfriend")
    suspend fun unFriend(@Body friendId: FriendId)

    @POST("/friends/reject-request")
    suspend fun rejectFriend(@Body friendId: FriendId)

    @POST("/friends/accept-request")
    suspend fun acceptFriend(@Body friendId: FriendId)

    @POST("/post/like/{postId}")
    suspend fun likePost(@Path("postId")postId:String):PostUserString

    @GET("/notification")
    suspend fun getNotification(): List<Notification>

}
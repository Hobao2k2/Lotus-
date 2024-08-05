package com.example.lotus.data.network

import android.media.session.MediaSession.Token
import com.example.lotus.data.model.User
import retrofit2.http.*
import okhttp3.MultipartBody
import retrofit2.Response

interface ApiService {
    @POST("users")
    suspend fun registerUser(
        @Body user: User
    ): Response<User>


}
package com.example.lotus.data.repository

import android.content.Context
import com.example.lotus.data.model.FriendId
import com.example.lotus.data.model.IdRequest
import com.example.lotus.data.model.Post
import com.example.lotus.data.model.PostUserId
import com.example.lotus.data.model.User
import com.example.lotus.data.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class UserRepository(context: Context) {
    private val api=RetrofitClient.getRetrofitClient(context)

    suspend fun getUserProfile():User{
        return api.getUserProfile()
    }

    suspend fun gelAllPost():List<Post>{
        return api.getAllPost()
    }

    suspend fun gelAllUsers():List<User>{
        return api.getAllUsers()
    }
    suspend fun searchUser(keyWord:String):List<User>{
        return api.getSearchUser(keyWord)
    }
    suspend fun getUserById(id:String):User{
        return api.getUsersById(id)
    }
    suspend fun addFriend(idRequest: IdRequest){
        return api.addFriend(idRequest)
    }

    suspend fun unFriend(friendId: FriendId){
        return api.unFriend(friendId)
    }
}
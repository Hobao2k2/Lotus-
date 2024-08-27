package com.example.lotus.data.repository

import android.content.Context
import com.example.lotus.data.model.FriendId
import com.example.lotus.data.model.IdRequest
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

    // Hàm trích xuất thông báo lỗi từ JSON
    private fun extractErrorMessage(errorJson: String?): String? {
        return try {
            val jsonObject = JSONObject(errorJson)
            jsonObject.optString("message", "Unknown error")
        } catch (e: JSONException) {
            "Unknown error"
        }
    }
    suspend fun getUserProfile():User{
        return api.getUserProfile()
    }
    suspend fun addPost(content: String, user: String, imageFile: File?): PostUserId {
        // Tạo RequestBody cho nội dung và user
        val contentRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), content)
        val userRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), user)

        // Tạo MultipartBody.Part cho hình ảnh nếu có
        val imagePart: MultipartBody.Part? = imageFile?.let {
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), it)
            MultipartBody.Part.createFormData("image", it.name, requestFile)
        }

        return api.addPost(contentRequestBody, imagePart)
    }

    suspend fun gelAllPost():List<PostUserId>{
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
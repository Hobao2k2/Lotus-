package com.example.lotus.data.repository

import android.content.Context
import android.net.http.HttpException
import android.provider.UserDictionary.Words
import com.example.lotus.data.model.Post
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.POST
import java.io.File
import java.io.IOException

class UserRepository(context: Context) {
    private val api=RetrofitClient.getRetrofitClient(context)

    suspend fun register(registerRequest: RegisterRequest): Resource<RegisterResponse> {
        return try {
            val response = api.register(registerRequest)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                // Lấy thông tin lỗi từ phản hồi
                val errorJson = response.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorJson) ?: "Unknown error"
                Resource.Error(errorMessage)
            }
        } catch (e: IOException) {
            Resource.Error("Network error")
        }
    }

    suspend fun login(registerRequest: RegisterRequest): Resource<String> {
        return try {
            val response = api.login(registerRequest)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                // Lấy thông tin lỗi từ phản hồi
                val errorJson = response.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorJson) ?: "Unknown error"
                Resource.Error(errorMessage)
            }
        } catch (e: IOException) {
            Resource.Error("Network error")
        }
    }

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
    suspend fun addPost(content: String, user: String, imageFile: File?): Post {
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

    suspend fun gelAllPost():List<Post>{
        return api.getAllPost()
    }

    suspend fun gelAllUsers():List<User>{
        return api.getAllUsers()
    }
    suspend fun searchUser(keyWord:String):List<User>{
        return api.getSearchUser(keyWord)
    }
}
package com.example.lotus.data.repository

import android.net.http.HttpException
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class UserRepository {
    private val api=RetrofitClient.getRetrofitClient()

    suspend fun register(registerRequest: RegisterRequest) = flow {
        try {
            val response = api.register(registerRequest)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorJson) ?: "Unknown error"
                emit(Resource.Error(errorMessage))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error"))
        }
    }

    suspend fun login(registerRequest: RegisterRequest) = flow {
        try {
            val response = api.login(registerRequest)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                // Lấy thông tin lỗi từ phản hồi
                val errorJson = response.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorJson) ?: "Unknown error"
                emit(Resource.Error(errorMessage))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error"))
        }
    }

    suspend fun getUserProfile():User{
        return api.getUserProfile()
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
}
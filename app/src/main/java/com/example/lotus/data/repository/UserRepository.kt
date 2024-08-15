package com.example.lotus.data.repository

import android.content.Context
import android.net.http.HttpException
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import com.example.lotus.data.network.ApiService
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class UserRepository (private val context: Context) {

    private val api: ApiService = RetrofitClient.getRetrofitClient(context)

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
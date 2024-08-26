package com.example.lotus.data.repository

import android.content.Context
import android.util.Log
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.network.ApiService
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(context: Context)  : AuthRepositoryIpm {

    private val apiService: ApiService = RetrofitClient.getRetrofitClient(context)

    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>> {
        return flow {
            val resource = try {
                val response = apiService.register(registerRequest)
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)

                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
            emit(resource)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun login(loginRequest: RegisterRequest): Flow<Resource<String>> {
        return flow {
            try {
                val response = apiService.login(loginRequest)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    emit(Resource.Success(responseBody ?: ""))
                } else {
                    emit(Resource.Error("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Exception: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

}
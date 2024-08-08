package com.example.lotus.data.repository

import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository : AuthRepositoryIpm {

//    private var apiService = RetrofitClient.getRetrofitClient()

    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>> {
        return flow {

        }
    }

    override suspend fun login(loginRequest: RegisterRequest): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

}
package com.example.lotus.data.repository

import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepositoryIpm {

    suspend fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>>
    suspend fun login(loginRequest: RegisterRequest): Flow<Resource<String>>

}
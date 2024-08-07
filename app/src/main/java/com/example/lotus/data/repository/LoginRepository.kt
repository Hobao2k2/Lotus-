package com.example.lotus.data.repository

import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository : DataRepositoryResource {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>> {
        return flow {

        }
    }

    override suspend fun login(loginRequest: RegisterRequest): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

}
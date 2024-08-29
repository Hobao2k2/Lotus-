package com.example.lotus.ui.viewModel.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.repository.AuthRepository
import com.example.lotus.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel (private val authRepository: AuthRepository) : ViewModel(){

    private val _registerResponse = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading())
    val registerResponse: StateFlow<Resource<RegisterResponse>> = _registerResponse.asStateFlow()

    private val _loginResponse = MutableStateFlow<Resource<String>>(Resource.Loading())
    val loginResponse: StateFlow<Resource<String>> = _loginResponse.asStateFlow()

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            authRepository.register(registerRequest).collect {
                _registerResponse.value = it
            }
        }
    }

    fun login(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            authRepository.login(registerRequest).collect {
                _loginResponse.value = it
            }
        }
    }



}
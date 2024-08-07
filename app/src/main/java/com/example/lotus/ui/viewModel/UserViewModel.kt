package com.example.lotus.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> = _userProfile.asStateFlow()

    private val _registerResponse = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading())
    val registerResponse: StateFlow<Resource<RegisterResponse>> = _registerResponse.asStateFlow()

    private val _loginResponse = MutableStateFlow<Resource<String>>(Resource.Loading())
    val loginResponse: StateFlow<Resource<String>> = _loginResponse.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            val data = userRepository.getUserProfile()
            _userProfile.value = data
        }
    }

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            userRepository.register(registerRequest).collect {
                _registerResponse.value = it
            }
        }
    }

    fun login(registerRequest: RegisterRequest) {
        viewModelScope.launch {
           userRepository.login(registerRequest).collect {
               _loginResponse.value = it
           }
        }
    }
}

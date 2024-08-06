package com.example.lotus.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userProfile= MutableStateFlow<User?>(null)
    val userProfile=_userProfile.asStateFlow()

    fun getUserProfile(){
        viewModelScope.launch {
            val data=userRepository.getUserProfile()
            _userProfile.value=data
        }
    }

    private val _registerResponse = MutableLiveData<Resource<RegisterResponse>>()
    val registerResponse: LiveData<Resource<RegisterResponse>> = _registerResponse

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _registerResponse.value = Resource.Loading()
            val response = userRepository.register(registerRequest)
            _registerResponse.value = response
        }
    }

    private val _loginResponse = MutableLiveData<Resource<String>>()
    val loginResponse: LiveData<Resource<String>> = _loginResponse

    fun login(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading()
            val response = userRepository.login(registerRequest)
            _loginResponse.value = response
        }
    }

}


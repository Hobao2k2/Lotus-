package com.example.lotus.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import com.example.lotus.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: UserRepository) : ViewModel(){

//    val registerResponse = MutableLiveData<RegisterResponse>()
//    val error = MutableLiveData<String>()
//
//    suspend fun register(registerRequest: RegisterRequest) {
//        viewModelScope.launch {
//            try {
//                val result = repository.register(registerRequest)
//                registerResponse.postValue(result)
//            } catch (e: Exception) {
//                error.postValue(e.message)
//            }
//        }
//    }

}
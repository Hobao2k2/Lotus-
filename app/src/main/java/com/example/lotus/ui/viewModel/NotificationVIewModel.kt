package com.example.lotus.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.Notification
import com.example.lotus.data.model.User
import com.example.lotus.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationVIewModel(private val userRepository: UserRepository):ViewModel() {
    private val _notification= MutableStateFlow<List<Notification>?>(null)
    val notification = _notification.asStateFlow()

    fun getNotification(){
        viewModelScope.launch {
            _notification.value=userRepository.getNotification()
        }
    }
}
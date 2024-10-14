package com.example.lotus.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lotus.data.repository.UserRepository

class NotificationViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotificationVIewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NotificationVIewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
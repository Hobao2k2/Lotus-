package com.example.lotus.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lotus.data.repository.UserRepository


class LoginViewModel (private val repository: UserRepository) : ViewModel() {

    val token = MutableLiveData<String>()


}
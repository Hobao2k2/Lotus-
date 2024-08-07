package com.example.lotus.data.model

data class RegisterRequest(

    val userName: String,

    val email: String,

    val password: String,

    val conformPassword: String

)
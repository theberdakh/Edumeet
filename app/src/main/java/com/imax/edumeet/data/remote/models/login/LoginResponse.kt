package com.imax.edumeet.data.remote.models.login

data class LoginResponse(
    val token: String,
    val user: User
)

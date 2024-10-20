package com.example.fullmovieapp_compose.auth.data.remote.dto

data class AuthRequestDto(
    val name: String = "",
    val email: String,
    val password: String = ""
)
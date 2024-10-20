package com.example.fullmovieapp_compose.auth.data.remote.dto

data class AuthRequest(
    val name: String = "",
    val email: String,
    val password: String = ""
)
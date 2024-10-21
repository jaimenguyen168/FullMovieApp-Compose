package com.example.fullmovieapp_compose.auth.presentation.register

data class RegisterState(
    val isLoading: Boolean = false,
    val name: String = "",
    val email: String = "",
    val password: String = "",
)

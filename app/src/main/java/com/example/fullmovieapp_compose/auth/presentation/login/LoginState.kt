package com.example.fullmovieapp_compose.auth.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
)

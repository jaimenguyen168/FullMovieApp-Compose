package com.example.fullmovieapp_compose.profile.presentation

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val isLogout: Boolean = false
)
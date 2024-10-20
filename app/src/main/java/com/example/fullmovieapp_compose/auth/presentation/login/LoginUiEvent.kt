package com.example.fullmovieapp_compose.auth.presentation.login

sealed class LoginUiEvent {
    data class OnEmailChanged(val newEmail: String): LoginUiEvent()
    data class OnPasswordChanged(val newPassword: String): LoginUiEvent()
    data object Login: LoginUiEvent()
}
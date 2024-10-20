package com.example.fullmovieapp_compose.auth.presentation.register

sealed class RegisterUiEvent {
    data class OnNameChanged(val newName: String): RegisterUiEvent()
    data class OnEmailChanged(val newEmail: String): RegisterUiEvent()
    data class OnPasswordChanged(val newPassword: String): RegisterUiEvent()
    data object Register: RegisterUiEvent()
}
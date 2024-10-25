package com.example.fullmovieapp_compose.profile.presentation

sealed class ProfileUiEvent {
    data object Logout : ProfileUiEvent()
}
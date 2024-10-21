package com.example.fullmovieapp_compose.main.presentation

import com.example.fullmovieapp_compose.util.Screen

sealed class MainUiEvent {
    data class Refresh(val route: Screen): MainUiEvent()
    data class Paginate(val route: Screen): MainUiEvent()

    data object LoadAll: MainUiEvent()
}
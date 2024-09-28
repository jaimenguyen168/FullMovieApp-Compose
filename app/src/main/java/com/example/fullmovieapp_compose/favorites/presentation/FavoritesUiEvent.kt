package com.example.fullmovieapp_compose.favorites.presentation

sealed class FavoritesUiEvent {

    data object Refresh: FavoritesUiEvent()
}
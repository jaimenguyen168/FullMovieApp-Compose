package com.example.fullmovieapp_compose.favorites.presentation

import com.example.fullmovieapp_compose.main.domain.model.Media

data class FavoritesState(
    val likedList: List<Media> = emptyList(),
    val bookmarkedList: List<Media> = emptyList(),
)
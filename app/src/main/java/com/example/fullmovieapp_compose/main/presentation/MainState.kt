package com.example.fullmovieapp_compose.main.presentation

import com.example.fullmovieapp_compose.main.domain.model.Media

data class MainState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,

    val trendingPage: Int = 1,
    val tvPage: Int = 1,
    val moviePage: Int = 1,

    val trendingList: List<Media> = emptyList(),
    val tvList: List<Media> = emptyList(),
    val movieList: List<Media> = emptyList(),

    // 2 from trending + 2 from TV + 2 from movie
    val specialList: List<Media> = emptyList(),

    val name: String = "Jaime"
)

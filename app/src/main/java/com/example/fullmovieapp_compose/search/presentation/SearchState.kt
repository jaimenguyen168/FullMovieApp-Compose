package com.example.fullmovieapp_compose.search.presentation

import com.example.fullmovieapp_compose.main.domain.model.Media

data class SearchState(
    val isLoading: Boolean = false,
    val searchPage: Int = 1,
    val searchQuery: String = "",
    val searchMediaList: List<Media> = emptyList()
)

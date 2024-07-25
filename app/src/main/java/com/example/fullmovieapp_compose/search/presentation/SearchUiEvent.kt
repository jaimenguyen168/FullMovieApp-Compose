package com.example.fullmovieapp_compose.search.presentation

import com.example.fullmovieapp_compose.main.domain.model.Media

sealed class SearchUiEvent {
    data class OnSearchQueryChange(val query: String): SearchUiEvent()
    data class OnSearchItemClick(val media: Media): SearchUiEvent()
    data object Paginate: SearchUiEvent()
}
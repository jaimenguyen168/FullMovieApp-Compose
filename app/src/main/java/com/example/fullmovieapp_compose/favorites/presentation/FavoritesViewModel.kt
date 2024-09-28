package com.example.fullmovieapp_compose.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullmovieapp_compose.favorites.domain.repo.FavoriteMediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteMediaRepository: FavoriteMediaRepository
): ViewModel() {

    private val _favoritesState = MutableStateFlow(FavoritesState())
    val favoritesState = _favoritesState.asStateFlow()

    init {
        loadFavorites()

        viewModelScope.launch {
            favoriteMediaRepository.favoriteMediaDbUpdateEventFlow().collect { isUpdated ->
                if (isUpdated) {
                    loadFavorites()
                }
            }
        }
    }

    fun onEvent(event: FavoritesUiEvent) {
        when (event) {
            is FavoritesUiEvent.Refresh -> {
                loadFavorites()
            }
        }
    }

    private fun loadFavorites() {
        loadLikedList()
        loadBookmarkedList()
    }

    private fun loadLikedList() {
        viewModelScope.launch {
            _favoritesState.update {
                it.copy(
                    likedList = favoriteMediaRepository.getLikedMediaList()
                )
            }
        }
    }

    private fun loadBookmarkedList() {
        viewModelScope.launch {
            _favoritesState.update {
                it.copy(
                    bookmarkedList = favoriteMediaRepository.getBookmarkedList()
                )
            }
        }
    }
}
























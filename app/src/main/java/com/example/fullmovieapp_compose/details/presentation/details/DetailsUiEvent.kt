package com.example.fullmovieapp_compose.details.presentation.details

sealed class DetailsUiEvent {
    data class StartLoading(val id: Int): DetailsUiEvent()

    data object Refresh: DetailsUiEvent()
    data object NavigateToWatchVideo: DetailsUiEvent()

    data class ShowOrHideAlertDialog(
        val alertType: Int = 0,
    ): DetailsUiEvent()

    data object LikeOrDislike: DetailsUiEvent()
    data object BookmarkOrUnBookmark: DetailsUiEvent()
}
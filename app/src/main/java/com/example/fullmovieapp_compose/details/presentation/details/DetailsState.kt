package com.example.fullmovieapp_compose.details.presentation.details

import com.example.fullmovieapp_compose.main.domain.model.Media

data class DetailsState(
    val isLoading: Boolean = false,
    val error: String = "",

    val media: Media? = null,
    val videoId: String = "",
    val readableTime: String = "",

    val videos: List<String> = emptyList(),
    val similarMediaList: List<Media> = emptyList(),
)

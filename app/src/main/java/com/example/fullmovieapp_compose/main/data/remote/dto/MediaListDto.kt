package com.example.fullmovieapp_compose.main.data.remote.dto

data class MediaListDto(
    val page: Int?,
    val results: List<MediaDto>?
)
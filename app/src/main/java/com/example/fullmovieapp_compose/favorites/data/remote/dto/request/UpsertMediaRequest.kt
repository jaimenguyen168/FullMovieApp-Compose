package com.example.fullmovieapp_compose.favorites.data.remote.dto.request

data class UpsertMediaRequest(
    val mediaRequest: MediaRequest,
    val email: String,
)

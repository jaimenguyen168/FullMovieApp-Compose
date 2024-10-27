package com.example.fullmovieapp_compose.favorites.domain.repo

import com.example.fullmovieapp_compose.favorites.data.remote.dto.request.MediaRequest
import com.example.fullmovieapp_compose.favorites.data.remote.dto.response.BackendMediaDto

interface BackendFavoritesRepository {
    suspend fun getLikedMediaList(): List<BackendMediaDto>?
    suspend fun getBookmarkedMediaList(): List<BackendMediaDto>?

    suspend fun getMediaById(
        mediaId: Int
    ): BackendMediaDto?

    suspend fun upsertMediaToUser(
        mediaRequest: MediaRequest
    ): Boolean

    suspend fun deleteMediaFromUser(
        mediaId: Int
    ): Boolean

}
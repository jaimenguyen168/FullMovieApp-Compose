package com.example.fullmovieapp_compose.favorites.domain.repo

import com.example.fullmovieapp_compose.main.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface FavoriteMediaRepository {

    // notify something has changed
    suspend fun favoriteMediaDbUpdateEventFlow(): Flow<Boolean>

    suspend fun upsertFavoriteMediaItem(
        media: Media
    )

    suspend fun deleteFavoriteMediaItem(
        media: Media
    )

    suspend fun getFavoriteMediaItemById(
        mediaId: Int
    ): Media?

    suspend fun getLikedMediaList(): List<Media>

    suspend fun getBookmarkedList(): List<Media>

//    suspend fun getAllFavoriteMediaItem(): List<Media>
//
//    suspend fun deleteAllFavoriteMediaItems()
}
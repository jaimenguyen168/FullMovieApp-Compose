package com.example.fullmovieapp_compose.favorites.data.repo

import com.example.fullmovieapp_compose.favorites.data.local.FavoriteMediaDatabase
import com.example.fullmovieapp_compose.favorites.data.mapper.toFavoriteMediaEntity
import com.example.fullmovieapp_compose.favorites.data.mapper.toMedia
import com.example.fullmovieapp_compose.favorites.domain.repo.FavoriteMediaRepository
import com.example.fullmovieapp_compose.main.data.local.MediaDatabase
import com.example.fullmovieapp_compose.main.domain.model.Media
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class FavoriteMediaRepositoryImpl @Inject constructor(
    favoriteMediaDatabase: FavoriteMediaDatabase,
    mediaDatabase: MediaDatabase,
): FavoriteMediaRepository {

    private val mediaDao = mediaDatabase.mediaDao
    private val favoriteMediaDao = favoriteMediaDatabase.favoriteMediaDao

    private val _favoriteMediaDbUpdateEventChannel = Channel<Boolean>()

    override suspend fun favoriteMediaDbUpdateEventFlow(): Flow<Boolean> =
        _favoriteMediaDbUpdateEventChannel.receiveAsFlow()

    override suspend fun upsertFavoriteMediaItem(media: Media) {
        favoriteMediaDao.upsertFavoriteMediaItem(
            favoriteMediaEntity = media.toFavoriteMediaEntity()
        )

        _favoriteMediaDbUpdateEventChannel.send(true)
    }

    override suspend fun deleteFavoriteMediaItem(media: Media) {
        favoriteMediaDao.deleteFavoriteMediaItem(
            favoriteMediaEntity = media.toFavoriteMediaEntity()
        )

        _favoriteMediaDbUpdateEventChannel.send(true)
    }

    override suspend fun getFavoriteMediaItemById(mediaId: Int): Media? =
        favoriteMediaDao.getFavoriteMediaItemById(
            mediaId = mediaId
        )?.toMedia()

    override suspend fun getLikedMediaList(): List<Media> =
        if (favoriteMediaDao.getLikedMediaList().isNotEmpty()) {
            favoriteMediaDao.getLikedMediaList().map { it.toMedia() }
        } else {
            emptyList()
        }

    override suspend fun getBookmarkedList(): List<Media> =
        if (favoriteMediaDao.getBookmarkedList().isNotEmpty()) {
            favoriteMediaDao.getBookmarkedList().map { it.toMedia() }
        } else {
            emptyList()
        }

//    override suspend fun getAllFavoriteMediaItem(): List<Media> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun deleteAllFavoriteMediaItems() {
//        TODO("Not yet implemented")
//    }
}





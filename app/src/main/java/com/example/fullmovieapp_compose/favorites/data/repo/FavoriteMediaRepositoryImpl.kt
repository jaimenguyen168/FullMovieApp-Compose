package com.example.fullmovieapp_compose.favorites.data.repo

import com.example.fullmovieapp_compose.favorites.data.local.FavoriteMediaDatabase
import com.example.fullmovieapp_compose.favorites.data.local.FavoriteMediaEntity
import com.example.fullmovieapp_compose.favorites.data.mapper.toFavoriteMediaEntity
import com.example.fullmovieapp_compose.favorites.data.mapper.toMedia
import com.example.fullmovieapp_compose.favorites.data.mapper.toMediaEntity
import com.example.fullmovieapp_compose.favorites.data.mapper.toMediaRequest
import com.example.fullmovieapp_compose.favorites.domain.repo.BackendFavoritesRepository
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
    private val backendFavoritesRepository: BackendFavoritesRepository
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

        syncFavoritesMedia()

        _favoriteMediaDbUpdateEventChannel.send(true)
    }

    override suspend fun deleteFavoriteMediaItem(media: Media) {
        val favoriteMediaEntity = media
            .toFavoriteMediaEntity()
            .copy(isDeletedLocally = true)

        favoriteMediaDao.upsertFavoriteMediaItem(
            favoriteMediaEntity = favoriteMediaEntity
        )

        syncFavoritesMedia()

        _favoriteMediaDbUpdateEventChannel.send(true)
    }

    override suspend fun getFavoriteMediaItemById(mediaId: Int): Media? {
        // return favorite media item from database
        favoriteMediaDao.getFavoriteMediaItemById(
            mediaId
        )?.let {
            return it.toMedia()
        }

        // return media item from backend if not in database
        backendFavoritesRepository.getMediaById(
            mediaId
        )?.let { backendMediaDto ->
            favoriteMediaDao.upsertFavoriteMediaItem(
                backendMediaDto.toFavoriteMediaEntity()
            )
            mediaDao.upsertMediaItem(
                backendMediaDto.toMediaEntity()
            )

            return favoriteMediaDao.getFavoriteMediaItemById(
                mediaId
            )?.toMedia()
        }

        return null
    }

    override suspend fun getLikedMediaList(): List<Media> =
        if (favoriteMediaDao.getLikedMediaList().isNotEmpty()) {
            favoriteMediaDao.getLikedMediaList().map { it.toMedia() }
        } else {
            backendFavoritesRepository
                .getLikedMediaList()?.let { backendMediaDtos ->
                    favoriteMediaDao.upsertFavoriteMediaList(
                        backendMediaDtos.map { it.toFavoriteMediaEntity() }
                    )
                    mediaDao.upsertMediaList(
                        backendMediaDtos.map { it.toMediaEntity() }
                    )

                    return favoriteMediaDao.getLikedMediaList().map {
                        it.toMedia()
                    }
                }

            emptyList()
        }

    override suspend fun getBookmarkedList(): List<Media> =
        if (favoriteMediaDao.getBookmarkedList().isNotEmpty()) {
            favoriteMediaDao.getBookmarkedList().map { it.toMedia() }
        } else {
            backendFavoritesRepository
                .getBookmarkedMediaList()?.let { backendMediaDtos ->
                    favoriteMediaDao.upsertFavoriteMediaList(
                        backendMediaDtos.map { it.toFavoriteMediaEntity() }
                    )
                    mediaDao.upsertMediaList(
                        backendMediaDtos.map { it.toMediaEntity() }
                    )

                    return favoriteMediaDao.getBookmarkedList().map {
                        it.toMedia()
                    }
                }

            emptyList()
        }

    override suspend fun clear() {
        favoriteMediaDao.deleteAllFavoriteMediaItems()
    }

    private suspend fun syncFavoritesMedia() {
        val favoriteMediaEntities = favoriteMediaDao.getAllFavoriteMediaItem()

        favoriteMediaEntities.forEach { favoriteMediaEntity ->
            if (favoriteMediaEntity.isDeletedLocally) {
                syncLocallyDeleteMedia(favoriteMediaEntity)
            } else if (!favoriteMediaEntity.isSynced) {
                syncUnsyncedMedia(favoriteMediaEntity)
            }
        }
    }

    private suspend fun syncLocallyDeleteMedia(
        favoriteMediaEntity: FavoriteMediaEntity
    ) {
        val wasDeleted = backendFavoritesRepository.deleteMediaFromUser(
            favoriteMediaEntity.mediaId
        )

        if (wasDeleted) {
            favoriteMediaDao.deleteFavoriteMediaItem(
                favoriteMediaEntity
            )
        }
    }

    private suspend fun syncUnsyncedMedia(
        favoriteMediaEntity: FavoriteMediaEntity
    ) {
        val wasSynced = backendFavoritesRepository.upsertMediaToUser(
            favoriteMediaEntity.toMediaRequest()
        )

        if (wasSynced) {
            favoriteMediaDao.upsertFavoriteMediaItem(
                favoriteMediaEntity.copy(isSynced = true)
            )
        }
    }

//    override suspend fun getAllFavoriteMediaItem(): List<Media> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun deleteAllFavoriteMediaItems() {
//        TODO("Not yet implemented")
//    }
}





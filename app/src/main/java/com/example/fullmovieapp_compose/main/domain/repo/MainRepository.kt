package com.example.fullmovieapp_compose.main.domain.repo

import com.example.fullmovieapp_compose.main.data.local.MediaEntity
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    // upsert media list
    suspend fun upsertMediaList(mediaList: List<Media>)

    // upsert media item
    suspend fun upsertMediaItem(mediaItem: Media)

    // get media list by category
    suspend fun getMediaListByCategory(
        category: String
    ): List<Media> // this is from database

    // get all Movies and TVs
    suspend fun getAllMoviesAndTVs(
        fetchFromRemote: Boolean,
        isRefreshing: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>>

    // get trending
    suspend fun getTrending(
        fetchFromRemote: Boolean,
        isRefreshing: Boolean,
        type: String,
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>>
}
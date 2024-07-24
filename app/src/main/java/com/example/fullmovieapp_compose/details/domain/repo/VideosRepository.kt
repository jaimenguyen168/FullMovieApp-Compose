package com.example.fullmovieapp_compose.details.domain.repo

import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface VideosRepository {
    suspend fun getVideos(
        id: Int,
        isRefreshing: Boolean
    ): Flow<Resource<List<String>>>
}
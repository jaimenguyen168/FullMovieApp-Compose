package com.example.fullmovieapp_compose.details.domain.repo

import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface SimilarRepository {
    suspend fun getSimilarMediaList(
        id: Int
    ): Flow<Resource<List<Media>>>
}
package com.example.fullmovieapp_compose.details.domain.repo

import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getDetails(
        id: Int,
        isRefreshing: Boolean
    ): Flow<Resource<Media>>
}
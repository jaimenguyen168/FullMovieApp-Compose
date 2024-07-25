package com.example.fullmovieapp_compose.search.domain.repo

import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchList(
        query: String,
        page: Int
    ): Flow<Resource<List<Media>>>

}
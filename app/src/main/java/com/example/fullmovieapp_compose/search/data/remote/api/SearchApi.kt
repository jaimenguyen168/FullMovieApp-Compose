package com.example.fullmovieapp_compose.search.data.remote.api

import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import com.example.fullmovieapp_compose.main.data.remote.dto.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/multi")
    suspend fun getSearchList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY
    ): MediaListDto?
}
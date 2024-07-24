package com.example.fullmovieapp_compose.details.data.remote.api

import com.example.fullmovieapp_compose.details.data.remote.dto.DetailsDto
import com.example.fullmovieapp_compose.details.data.remote.dto.VideoDtoList
import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import com.example.fullmovieapp_compose.main.data.remote.dto.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi {
    @GET("{type}/{id}")
    suspend fun getDetails(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY
    ): DetailsDto?

    @GET("{type}/{id}/videos")
    suspend fun getVideos(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY
    ): VideoDtoList?

    @GET("{type}/{id}/similar")
    suspend fun getSimilarMediaList(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY
    ): MediaListDto?
}
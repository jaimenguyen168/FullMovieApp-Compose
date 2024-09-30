package com.example.fullmovieapp_compose.categories.domain.repo

import com.example.fullmovieapp_compose.main.domain.model.Media

interface CategoriesRepository {

    suspend fun getActionAndAdventure(): List<Media>
    suspend fun getDrama(): List<Media>
    suspend fun getComedy(): List<Media>
    suspend fun getSciFiAndFantasy(): List<Media>
    suspend fun getAnimation(): List<Media>
}
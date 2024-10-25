package com.example.fullmovieapp_compose.profile.domain.repo

interface ProfileRepository {
    suspend fun getName(): String
    suspend fun getEmail(): String
}
package com.example.fullmovieapp_compose.auth.domain

import com.example.fullmovieapp_compose.auth.util.AuthResult

interface AuthRepository {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthResult<Unit>

    suspend fun login(
        email: String,
        password: String
    ): AuthResult<Unit>

    suspend fun authenticate(): AuthResult<Unit>

    suspend fun logout(): AuthResult<Unit>
}








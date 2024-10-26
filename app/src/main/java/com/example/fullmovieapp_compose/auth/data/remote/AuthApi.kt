package com.example.fullmovieapp_compose.auth.data.remote

import com.example.fullmovieapp_compose.auth.data.remote.dto.AuthRequest
import com.example.fullmovieapp_compose.auth.data.remote.dto.AuthResponse
import com.example.fullmovieapp_compose.util.BackendConstants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST(BackendConstants.REGISTER)
    suspend fun register(
        @Body authRequest: AuthRequest
    )

    @POST(BackendConstants.LOGIN)
    suspend fun login(
        @Body authRequest: AuthRequest
    ): AuthResponse

    @GET(BackendConstants.AUTHENTICATE)
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}
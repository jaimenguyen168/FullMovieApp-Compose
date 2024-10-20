package com.example.fullmovieapp_compose.auth.data.remote

import com.example.fullmovieapp_compose.auth.data.remote.dto.AuthRequestDto
import com.example.fullmovieapp_compose.util.BackendConstants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST(BackendConstants.REGISTER)
    suspend fun register(
        @Body authRequestDto: AuthRequestDto
    )

    @POST(BackendConstants.LOGIN)
    suspend fun login(
        @Body authRequestDto: AuthRequestDto
    )

    @GET(BackendConstants.AUTHENTICATE)
    suspend fun authenticate(
        @Body authRequestDto: AuthRequestDto
    )
}
package com.example.fullmovieapp_compose.auth.data.repo

import android.content.SharedPreferences
import com.example.fullmovieapp_compose.auth.data.remote.AuthApi
import com.example.fullmovieapp_compose.auth.data.remote.dto.AuthRequest
import com.example.fullmovieapp_compose.auth.domain.repo.AuthRepository
import com.example.fullmovieapp_compose.auth.util.AuthResult
import com.example.fullmovieapp_compose.favorites.domain.repo.FavoriteMediaRepository
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val mainRepository: MainRepository,
    private val favoriteMediaRepository: FavoriteMediaRepository,
    private val sharedPreferences: SharedPreferences
): AuthRepository {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthResult<Unit> = try {
        authApi.register(
            AuthRequest(name, email, password)
        )
        login(email, password)
    } catch (e: HttpException) {
        e.printStackTrace()
        if (e.code() == 401) {
            AuthResult.Unauthorized()
        } else {
            AuthResult.UnknownError()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        AuthResult.UnknownError()
    }

    override suspend fun login(
        email: String,
        password: String
    ): AuthResult<Unit> = try {
        val response = authApi.login(
            AuthRequest(email, password)
        )

        sharedPreferences.edit().putString("email", email).apply()
        sharedPreferences.edit().putString("name", response.name).apply()
        sharedPreferences.edit().putString("token", response.token).apply()

        AuthResult.Authorized()
    } catch (e: HttpException) {
        e.printStackTrace()
        if (e.code() == 401) {
            AuthResult.Unauthorized()
        } else {
            AuthResult.UnknownError()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        AuthResult.UnknownError()
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = sharedPreferences.getString(
                "token", null
            ) ?: return AuthResult.Unauthorized()

            val response = authApi.authenticate(token)
            AuthResult.Authorized()

        } catch (e: HttpException) {
            e.printStackTrace()
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.UnknownError()
        }
    }

    override suspend fun logout(): AuthResult<Unit> {
        sharedPreferences.edit().putString("email", null).apply()
        sharedPreferences.edit().putString("name", null).apply()
        sharedPreferences.edit().putString("token", null).apply()

        mainRepository.clear()
        favoriteMediaRepository.clear()

        return AuthResult.SingedOut()
    }
}






















package com.example.fullmovieapp_compose.auth.data.repo

import android.content.SharedPreferences
import android.util.Log
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
            AuthRequest(name = name, email = email, password = password)
        )
        login(email, password)
    } catch (e: HttpException) {
        e.printStackTrace()
        if (e.code() == 401) {
            AuthResult.Unauthorized()
        } else {
            Log.i("Register Error Code", "${e.code()}, ${e.message()}")
            AuthResult.UnknownError()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.i("Register Error Code From Catch", "${e.message}")
        AuthResult.UnknownError()
    }

    override suspend fun login(
        email: String,
        password: String
    ): AuthResult<Unit> = try {
        val response = authApi.login(
            AuthRequest(email = email, password = password)
        )

        sharedPreferences.edit().putString("email", email).apply()
        sharedPreferences.edit().putString("name", response.name).apply()
        sharedPreferences.edit().putString("token", response.token).apply()

        Log.i("Authenticate Error Token in Login", "${response.token}")

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

            Log.i("Authenticate Error Token", "$token")

            authApi.authenticate(token)
            AuthResult.Authorized()

        } catch (e: HttpException) {
            e.printStackTrace()
            if (e.code() == 401) {
                Log.i("Authenticate Error Code", "${e.code()}, ${e.message()}")
//                AuthResult.Authorized()
                AuthResult.Unauthorized()
            } else {
                Log.i("Authenticate Error Code", "${e.code()}, ${e.message()}")
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Authenticate Error Code From Catch", "${e.message}")
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






















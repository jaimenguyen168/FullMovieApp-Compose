package com.example.fullmovieapp_compose.favorites.data.repo

import android.content.SharedPreferences
import coil.network.HttpException
import com.example.fullmovieapp_compose.favorites.data.remote.api.BackendMediaApi
import com.example.fullmovieapp_compose.favorites.data.remote.dto.request.MediaByIdRequest
import com.example.fullmovieapp_compose.favorites.data.remote.dto.request.MediaRequest
import com.example.fullmovieapp_compose.favorites.data.remote.dto.request.UpsertMediaRequest
import com.example.fullmovieapp_compose.favorites.data.remote.dto.response.BackendMediaDto
import com.example.fullmovieapp_compose.favorites.domain.repo.BackendFavoritesRepository
import com.google.gson.JsonIOException
import javax.inject.Inject

class BackendFavoritesRepositoryImpl @Inject constructor(
    private val backendMediaApi: BackendMediaApi,
    private val prefs: SharedPreferences
) : BackendFavoritesRepository {
    override suspend fun getLikedMediaList(): List<BackendMediaDto>? {
        val email = prefs.getString("email", null) ?: return null

        return try {
            backendMediaApi.getLikedMediaList(email)
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getBookmarkedMediaList(): List<BackendMediaDto>? {
        val email = prefs.getString("email", null) ?: return null

        return try {
            backendMediaApi.getBookmarkedMediaList(email)
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMediaById(mediaId: Int): BackendMediaDto? {
        val email = prefs.getString("email", null)
            ?: return null

        return try {
            backendMediaApi.getMediaById(
                mediaId.toString(), email
            )
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }

    }

    override suspend fun upsertMediaToUser(mediaRequest: MediaRequest): Boolean {
        val email = prefs.getString("email", null)
            ?: return false

        return try {
            backendMediaApi.upsertMediaToUser(
                UpsertMediaRequest(
                    mediaRequest, email
                )
            )
            true
        } catch (e: HttpException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteMediaFromUser(mediaId: Int): Boolean {
        val email = prefs.getString("email", null)
            ?: return false

        return try {
            backendMediaApi.deleteMediaFromUser(
                MediaByIdRequest(
                    mediaId, email
                )
            )
            true
        } catch (e: JsonIOException) {
            true
        } catch (e: HttpException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}


















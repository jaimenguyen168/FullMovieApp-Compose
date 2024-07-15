package com.example.fullmovieapp_compose.main.data.repo

import android.app.Application
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.main.data.local.MediaDao
import com.example.fullmovieapp_compose.main.data.local.MediaDatabase
import com.example.fullmovieapp_compose.main.data.mapper.toMedia
import com.example.fullmovieapp_compose.main.data.mapper.toMediaEntity
import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import com.example.fullmovieapp_compose.util.APIConstants.MOVIE
import com.example.fullmovieapp_compose.util.APIConstants.POPULAR
import com.example.fullmovieapp_compose.util.APIConstants.TRENDING
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

private const val s = "Couldn't reach server. Check your internet connection"

class MainRepositoryImpl(
    private val app: Application,
    private val mediaApi: MediaApi,
    mediaDatabase: MediaDatabase
): MainRepository {

    private val mediaDao: MediaDao = mediaDatabase.mediaDao
    override suspend fun upsertMediaList(mediaList: List<Media>) {
        mediaDao.upsertMediaList(mediaList.map { it.toMediaEntity() })
    }

    override suspend fun upsertMediaItem(mediaItem: Media) {
        mediaDao.upsertMediaItem(mediaItem.toMediaEntity())
    }

    override suspend fun getMediaListByCategory(category: String): List<Media> =
        mediaDao.getMediaListByCategory(category).map { it.toMedia() }


    override suspend fun getAllMoviesAndTVs(
        fetchFromRemote: Boolean,
        isRefreshing: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>> = flow {
        emit(Resource.Loading(true))

        val localMediaList = mediaDao.getMediaListByTypeAndCategory(
            mediaType = type,
            category = POPULAR
        )

        val shouldLoadFromCache =
            localMediaList.isNotEmpty() && !fetchFromRemote && !isRefreshing

        if (shouldLoadFromCache) {
            emit(Resource.Success(localMediaList.map { it.toMedia() }))
            emit(Resource.Loading(false))
            return@flow
        }

        val remoteMediaList = try {
            mediaApi.getMoviesAndTVs(
                type = type,
                category = POPULAR,
                page = if (isRefreshing) 1 else page
            )?.results
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(app.getString(R.string.server_error)))
            emit(Resource.Loading(false))
            return@flow
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(app.getString(R.string.server_error)))
            emit(Resource.Loading(false))
            return@flow
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(app.getString(R.string.server_error)))
            emit(Resource.Loading(false))
            return@flow
        }

        remoteMediaList?.let { mediaListDto ->
            val entities = mediaListDto.map { mediaDto ->
                mediaDto.toMediaEntity(
                    type = type,
                    category = POPULAR
                )
            }

            // delete trending media items if we are refreshing
            if (isRefreshing) {
                mediaDao.deleteMediaItemsByTypeAndCategory(
                    mediaType = type,
                    category = POPULAR
                )
            }

            // upsert media list to database
            mediaDao.upsertMediaList(entities)

            // emit media list from the api
            emit(Resource.Success(entities.map { it.toMedia() }))
            emit(Resource.Loading(false))
            return@flow
        }

        // if the remoteMediaList is null, we have an error
        emit(Resource.Error(app.getString(R.string.server_error)))
        emit(Resource.Loading(false))
        return@flow
    }

    override suspend fun getTrending(
        fetchFromRemote: Boolean,
        isRefreshing: Boolean,
        type: String,
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>> = flow {
        emit(Resource.Loading(true))

        val localMediaList = mediaDao.getMediaListByCategory(TRENDING)
        val shouldLoadFromCache =
            localMediaList.isNotEmpty() && !fetchFromRemote && !isRefreshing

        if (shouldLoadFromCache) {
            emit(Resource.Success(localMediaList.map { it.toMedia() }))
            emit(Resource.Loading(false))
            return@flow
        }

        val remoteMediaList = try {
            mediaApi.getTrending(
                type = type,
                time = time,
                page = if (isRefreshing) 1 else page
            )?.results
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(app.getString(R.string.server_error)))
            emit(Resource.Loading(false))
            return@flow
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(app.getString(R.string.server_error)))
            emit(Resource.Loading(false))
            return@flow
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(app.getString(R.string.server_error)))
            emit(Resource.Loading(false))
            return@flow
        }

        remoteMediaList?.let { mediaListDto ->
            val entities = mediaListDto.map { mediaDto ->
                mediaDto.toMediaEntity(
                    type = mediaDto.media_type ?: MOVIE,
                    category = TRENDING
                )
            }

            // delete trending media items if we are refreshing
            if (isRefreshing) {
                mediaDao.deleteMediaItemsByCategory(TRENDING)
            }

            // upsert media list to database
            mediaDao.upsertMediaList(entities)

            // emit media list from the api
            emit(Resource.Success(entities.map { it.toMedia() }))
            emit(Resource.Loading(false))
            return@flow
        }

        // if the remoteMediaList is null, we have an error
        emit(Resource.Error(app.getString(R.string.server_error)))
        emit(Resource.Loading(false))
        return@flow
    }
}
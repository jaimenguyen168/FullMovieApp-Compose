package com.example.fullmovieapp_compose.details.data.repo

import android.app.Application
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.data.remote.api.DetailsApi
import com.example.fullmovieapp_compose.details.domain.repo.VideosRepository
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val mainRepository: MainRepository,
    private val app: Application
): VideosRepository {
    override suspend fun getVideos(
        id: Int,
        isRefreshing: Boolean
    ): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading(true))

        val mediaItem = mainRepository.getMediaById(id)

        val doVideosExist = mediaItem.videoIds.isNotEmpty()

        if (doVideosExist && !isRefreshing) {
            emit(Resource.Success(mediaItem.videoIds))
            emit(Resource.Loading(false))
            return@flow
        }

        val remoteVideos = getVideosFromRemote(
            type = mediaItem.mediaType,
            id = id
        )

        remoteVideos?.let { videoIds ->
            if (videoIds.isNotEmpty()) {
                mainRepository.upsertMediaItem(
                    mediaItem.copy(
                        videoIds = videoIds
                    )
                )
                emit(Resource.Success(
                    mainRepository.getMediaById(id).videoIds
                ))
            } else {
                emit(Resource.Error(app.getString(R.string.server_error)))
            }

            emit(Resource.Loading(false))
            return@flow
        }

        emit(Resource.Error(app.getString(R.string.server_error)))
        emit(Resource.Loading(false))
        return@flow
    }

    private suspend fun getVideosFromRemote(
        type: String,
        id: Int,
    ): List<String>? {
        val remoteVideos =try {
            detailsApi.getVideos(type, id)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        val videos = remoteVideos?.results?.filter {
            it.site == "YouTube" && it.key?.isNotEmpty() == true
        }

        return  videos?.map { it.key!! }
    }
}
package com.example.fullmovieapp_compose.details.data.repo

import android.app.Application
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.data.remote.api.DetailsApi
import com.example.fullmovieapp_compose.details.data.remote.dto.DetailsDto
import com.example.fullmovieapp_compose.details.domain.repo.DetailsRepository
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val mainRepository: MainRepository,
    private val app: Application
): DetailsRepository {

    override suspend fun getDetails(
        id: Int,
        isRefreshing: Boolean
    ): Flow<Resource<Media>> = flow {
        emit(Resource.Loading(true))

        val mediaItem = mainRepository.getMediaById(id)

        val doDetailsExist = mediaItem.runtime != 0 || mediaItem.tagLine.isNotEmpty()

        if (doDetailsExist && !isRefreshing) {
            emit(Resource.Success(mediaItem))
            emit(Resource.Loading(false))
            return@flow
        }

        val remoteDetails = getRemoteDetails(
            type = mediaItem.mediaType,
            id = id
        )

        remoteDetails?.let { detailsDto ->
            val mediaWithDetails = mediaItem.copy(
                runtime = detailsDto.runtime ?: 0,
                tagLine = detailsDto.tagLine ?: ""
            )

            mainRepository.upsertMediaItem(mediaWithDetails)

            emit(Resource.Success(mainRepository.getMediaById(id)))
            emit(Resource.Loading(false))
            return@flow
        }

        emit(Resource.Error(app.getString(R.string.server_error)))
        emit(Resource.Loading(false))
    }

    private suspend fun getRemoteDetails(
        type: String,
        id: Int,
    ): DetailsDto? {
        return try {
            detailsApi.getDetails(type, id)
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
    }
}


















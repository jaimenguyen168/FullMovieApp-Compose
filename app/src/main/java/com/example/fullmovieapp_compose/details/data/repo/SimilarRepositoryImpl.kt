package com.example.fullmovieapp_compose.details.data.repo

import android.app.Application
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.data.remote.api.DetailsApi
import com.example.fullmovieapp_compose.details.domain.repo.SimilarRepository
import com.example.fullmovieapp_compose.favorites.domain.repo.FavoriteMediaRepository
import com.example.fullmovieapp_compose.main.data.mapper.toMedia
import com.example.fullmovieapp_compose.main.data.remote.dto.MediaDto
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SimilarRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val mainRepository: MainRepository,
    private val app: Application,
    private val favoriteMediaRepository: FavoriteMediaRepository
): SimilarRepository {
    override suspend fun getSimilarMediaList(
        id: Int
    ): Flow<Resource<List<Media>>> = flow {
        emit(Resource.Loading(true))

        // get the media then its similar list using its id
        val media = mainRepository.getMediaById(id)
        val localSimilarList = mainRepository.getMediaListByIds(media.similarMediaIds)

        if (localSimilarList.isNotEmpty()) {
            emit(Resource.Success(localSimilarList))
            emit(Resource.Loading(false))
            return@flow
        }

        // get similar media list dto from the api
        val remoteSimilarList = getRemoteSimilarList(
            type = media.mediaType,
            id = media.mediaId
        )

        // upsert media item with the list of similar media ids to database
        remoteSimilarList?.let { similarMediaListDto ->
            val similarIds = similarMediaListDto.map { it.id ?: 0 }

            mainRepository.upsertMediaItem(
                media.copy(
                    similarMediaIds = similarIds
                )
            )

            // convert similar media list dto to media list
            val similarMediaList = similarMediaListDto.map { similarMediaDto ->
                val favoriteMediaItem =
                    favoriteMediaRepository
                        .getFavoriteMediaItemById(similarMediaDto.id ?: 0
                    )

                similarMediaDto.toMedia(
                    category = media.category,
                    isLiked = favoriteMediaItem?.isLiked ?: false,
                    isBookmarked = favoriteMediaItem?.isBookmarked ?: false
                )
            }

            // upsert similar media list to database
            mainRepository.upsertMediaList(similarMediaList)

            // get similar media list from database after upsert
            emit(Resource.Success(
                mainRepository.getMediaListByIds(similarIds)
            ))
            emit(Resource.Loading(false))
            return@flow
        }

        emit(Resource.Error(app.getString(R.string.server_error)))
        emit(Resource.Loading(false))
        return@flow
    }

    private suspend fun getRemoteSimilarList(
        type: String,
        id: Int,
    ): List<MediaDto>? {
        val remoteSimilarList = try {

            // TODO() getting media with paginating (homework)
            detailsApi.getSimilarMediaList(
                type, id, 1
            )

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

        return remoteSimilarList?.results
    }
}
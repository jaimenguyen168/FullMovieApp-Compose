package com.example.fullmovieapp_compose.search.data.repo

import android.app.Application
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.main.data.mapper.toMedia
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.search.data.remote.api.SearchApi
import com.example.fullmovieapp_compose.search.domain.repo.SearchRepository
import com.example.fullmovieapp_compose.util.APIConstants.POPULAR
import com.example.fullmovieapp_compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val app: Application
): SearchRepository {
    override suspend fun getSearchList(
        query: String,
        page: Int
    ): Flow<Resource<List<Media>>> = flow {
        emit(Resource.Loading(true))

        val remoteSearchList = try {
            searchApi.getSearchList(
                query = query,
                page = page
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

        remoteSearchList?.let { mediaListDto ->
            val searchMediaList = mediaListDto.map { it.toMedia(POPULAR) }

            emit(Resource.Success(searchMediaList))
            emit(Resource.Loading(false))
            return@flow
        }

        emit(Resource.Error(app.getString(R.string.server_error)))
        emit(Resource.Loading(false))
    }
}

















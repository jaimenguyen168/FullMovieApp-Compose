package com.example.fullmovieapp_compose.details.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullmovieapp_compose.details.domain.repo.DetailsRepository
import com.example.fullmovieapp_compose.details.domain.repo.SimilarRepository
import com.example.fullmovieapp_compose.details.domain.repo.VideosRepository
import com.example.fullmovieapp_compose.details.domain.usecase.MinutesToReadableTime
import com.example.fullmovieapp_compose.favorites.domain.repo.FavoriteMediaRepository
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import com.example.fullmovieapp_compose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val detailsRepository: DetailsRepository,
    private val videoRepository: VideosRepository,
    private val similarRepository: SimilarRepository,
    private val favoriteMediaRepository: FavoriteMediaRepository
): ViewModel() {

    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    private val _navigateToWatchVideoChannel = Channel<Boolean>()
    val navigateToWatchVideoChannel = _navigateToWatchVideoChannel.receiveAsFlow()

    fun onEvent(event: DetailsUiEvent) {
        when(event) {
            is DetailsUiEvent.StartLoading -> {
                loadMediaItem(id = event.id)
            }
            DetailsUiEvent.NavigateToWatchVideo -> {
                viewModelScope.launch {
                    if (detailsState.value.videoId.isNotEmpty()) {
                        _navigateToWatchVideoChannel.send(true)
                    } else {
                        _navigateToWatchVideoChannel.send(false)
                    }
                }
            }
            DetailsUiEvent.Refresh -> {
                loadMediaItem(isRefreshing = true)
            }

            DetailsUiEvent.BookmarkOrUnBookmark -> bookmarkOrUnBookmark()
            DetailsUiEvent.LikeOrDislike -> likeOrDislike()
            is DetailsUiEvent.ShowOrHideAlertDialog -> {
                val media = detailsState.value.media

                if (event.alertType == 1 && media?.isLiked == false) {
                    likeOrDislike()
                    return
                }

                if (event.alertType == 2 && media?.isBookmarked == false) {
                    bookmarkOrUnBookmark()
                    return
                }

                _detailsState.update {
                    it.copy(
                        showAlertDialog = !it.showAlertDialog,
                        alertType = event.alertType
                    )
                }
            }
        }
    }

    private fun loadMediaItem(
        id: Int = detailsState.value.media?.mediaId ?: 0,
        isRefreshing: Boolean = false
    ) {
        viewModelScope.launch {
            _detailsState.update {
                it.copy(media = mainRepository.getMediaById(id))
            }

            viewModelScope.launch {
                loadDetails(isRefreshing)
                loadVideos(isRefreshing)
                loadSimilarMedia(isRefreshing)
            }
        }
    }

    private suspend fun loadDetails(
        isRefreshing: Boolean
    ) {
        detailsRepository.getDetails(
            id = detailsState.value.media?.mediaId ?: 0,
            isRefreshing = isRefreshing
        ).collect { result ->
            when(result) {
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    result.data?.let { media ->
                        _detailsState.update { state ->
                            // update the media with the details
                            state.copy(
                                media = state.media?.copy(
                                    runtime = media.runtime,
                                    tagLine = media.tagLine
                                ),
                                readableTime = if (media.runtime != 0) {
                                    MinutesToReadableTime(media.runtime).invoke()
                                } else {
                                    ""
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadVideos(
        isRefreshing: Boolean
    ) {
        videoRepository.getVideos(
            id = detailsState.value.media?.mediaId ?: 0,
            isRefreshing = isRefreshing
        ).collect { result ->
            when(result) {
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    result.data?.let { videoList ->
                        _detailsState.update { state ->
                            state.copy(
                                videos = videoList,
                                videoId = if (videoList.isNotEmpty()) {
                                    videoList.shuffled()[0]
                                } else {
                                    ""
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadSimilarMedia(
        isRefreshing: Boolean // TODO() for homework
    ) {
        similarRepository.getSimilarMediaList(
            id = detailsState.value.media?.mediaId ?: 0,
        ).collect { result ->
            when(result) {
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    result.data?.let { similarMediaList ->
                        _detailsState.update { state ->
                            state.copy(
                                similarMediaList = similarMediaList,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun likeOrDislike() {
        _detailsState.update {
            it.copy(
                media = it.media?.copy(
                    isLiked = !it.media.isLiked
                ),
                alertType = 0,
                showAlertDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun bookmarkOrUnBookmark() {
        _detailsState.update {
            it.copy(
                media = it.media?.copy(
                    isBookmarked = !it.media.isBookmarked
                ),
                alertType = 0,
                showAlertDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun updateOrDeleteMedia() {
        viewModelScope.launch {
            detailsState.value.media?.let { media ->
                if (!media.isLiked && !media.isBookmarked) {
                    favoriteMediaRepository.deleteFavoriteMediaItem(media)
                } else {
                    favoriteMediaRepository.upsertFavoriteMediaItem(media)

                    mainRepository.upsertMediaItem(media)
                }
            }
        }
    }
}






















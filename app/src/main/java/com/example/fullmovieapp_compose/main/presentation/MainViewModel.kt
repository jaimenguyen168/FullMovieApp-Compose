package com.example.fullmovieapp_compose.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import com.example.fullmovieapp_compose.util.APIConstants.ALL
import com.example.fullmovieapp_compose.util.APIConstants.MOVIE
import com.example.fullmovieapp_compose.util.APIConstants.POPULAR
import com.example.fullmovieapp_compose.util.APIConstants.TRENDING_TIME
import com.example.fullmovieapp_compose.util.APIConstants.TV
import com.example.fullmovieapp_compose.util.Resource
import com.example.fullmovieapp_compose.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
//    private val favoriteMediaRepository: FavoriteMediaRepository
): ViewModel() {
    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        loadTrending()
        loadTV()
        loadMovies()

//        observeFavoriteMediaUpdates()
    }

//    private fun observeFavoriteMediaUpdates() {
//        viewModelScope.launch {
//            favoriteMediaRepository.favoriteMediaDbUpdateEventFlow().collect { updateEvent ->
//                if (updateEvent) {
//                    loadTrending()
//                    loadTV()
//                    loadMovies()
//                }
//            }
//        }
//    }

    fun event(mainUiEvent: MainUiEvent) {
        when (mainUiEvent) {
            is MainUiEvent.Refresh -> {
                when(mainUiEvent.route) {
                    Screen.MainScreen -> {
                        _mainState.update {
                            it.copy(specialList = emptyList(),)
                        }

                        loadTrending(
                            fetchFromRemote = true,
                            isRefreshing = true
                        )
                        loadTV(
                            fetchFromRemote = true,
                            isRefreshing = true
                        )
                        loadMovies(
                            fetchFromRemote = true,
                            isRefreshing = true
                        )
                    }
                    Screen.MovieScreen -> {
                        loadMovies(
                            fetchFromRemote = true,
                            isRefreshing = true
                        )
                    }
                    Screen.TVScreen -> {
                        loadTV(
                            fetchFromRemote = true,
                            isRefreshing = true
                        )
                    }
                    Screen.TrendingScreen -> {
                        loadTrending(
                            fetchFromRemote = true,
                            isRefreshing = true
                        )
                    }
                    else -> Unit
                }
            }
            is MainUiEvent.Paginate -> {
                when(mainUiEvent.route) {
                    Screen.MainScreen -> TODO()
                    Screen.MovieScreen -> loadMovies(fetchFromRemote = true)
                    Screen.TVScreen -> loadTV(fetchFromRemote = true)
                    Screen.TrendingScreen -> loadTrending(fetchFromRemote = true)
                    else -> Unit
                }
            }
        }
    }

    private fun loadSpecial(list: List<Media>) {
        if (mainState.value.specialList.size <= 4) {
            _mainState.update {
                it.copy(
                    specialList = it.specialList + list.take(2)
                )
            }
        }
    }

    private fun loadTrending(
        fetchFromRemote: Boolean = false,
        isRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {
            mainRepository.getTrending(
                fetchFromRemote = fetchFromRemote,
                isRefreshing = isRefreshing,
                type = ALL,
                time = TRENDING_TIME,
                page = mainState.value.trendingPage
            ).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { mediaList ->
                            val shuffleList = mediaList.shuffled()

                            if (isRefreshing) {
                                _mainState.update {
                                    it.copy(
                                        trendingList = shuffleList,
                                        trendingPage = 2
                                    )
                                }
                                loadSpecial(shuffleList)
                            } else {
                                _mainState.update {
                                    it.copy(
                                        trendingList = it.trendingList + shuffleList,
                                        trendingPage = mainState.value.trendingPage + 1
                                    )
                                }

                                // not paginating
                                if (!fetchFromRemote) {
                                    loadSpecial(shuffleList)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadTV(
        fetchFromRemote: Boolean = false,
        isRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {
            mainRepository.getAllMoviesAndTVs(
                fetchFromRemote = fetchFromRemote,
                isRefreshing = isRefreshing,
                type = TV,
                category = POPULAR,
                page = mainState.value.tvPage
            ).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { mediaList ->
                            val shuffleList = mediaList.shuffled()

                            if (isRefreshing) {
                                _mainState.update {
                                    it.copy(
                                        tvList = shuffleList,
                                        tvPage = 2
                                    )
                                }
                                loadSpecial(shuffleList)

                            } else {
                                _mainState.update {
                                    it.copy(
                                        tvList = it.tvList + shuffleList,
                                        tvPage = mainState.value.tvPage + 1
                                    )
                                }

                                // not paginating
                                if (!fetchFromRemote) {
                                    loadSpecial(shuffleList)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadMovies(
        fetchFromRemote: Boolean = false,
        isRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {
            mainRepository.getAllMoviesAndTVs(
                fetchFromRemote = fetchFromRemote,
                isRefreshing = isRefreshing,
                type = MOVIE,
                category = POPULAR,
                page = mainState.value.moviePage
            ).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { mediaList ->
                            val shuffleList = mediaList.shuffled()

                            if (isRefreshing) {
                                _mainState.update {
                                    it.copy(
                                        movieList = shuffleList,
                                        moviePage = 2
                                    )
                                }
                                loadSpecial(shuffleList)

                            } else {
                                _mainState.update {
                                    it.copy(
                                        movieList = it.movieList + shuffleList,
                                        moviePage = mainState.value.moviePage + 1
                                    )
                                }

                                // not paginating
                                if (!fetchFromRemote) {
                                    loadSpecial(shuffleList)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
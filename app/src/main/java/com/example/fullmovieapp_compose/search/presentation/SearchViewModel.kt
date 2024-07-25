package com.example.fullmovieapp_compose.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import com.example.fullmovieapp_compose.search.domain.repo.SearchRepository
import com.example.fullmovieapp_compose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val mainRepository: MainRepository
): ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    private val _navigateToDetailsChannel = Channel<Int>()
    val navigateToDetailsChannel = _navigateToDetailsChannel.receiveAsFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchUiEvent) {
        when(event) {
            is SearchUiEvent.OnSearchItemClick -> {
                viewModelScope.launch {
                    mainRepository.upsertMediaItem(
                        mediaItem = event.media
                    )
                    _navigateToDetailsChannel.send(event.media.mediaId)
                }
            }
            is SearchUiEvent.OnSearchQueryChange -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500) // to avoid searching constantly
                    _searchState.update {
                        it.copy(
                            searchQuery = event.query,
                            searchMediaList = emptyList()
                        )
                    }

                    if (searchState.value.searchQuery.isNotEmpty()) {
                        loadSearchList()
                    }
                }
            }
            SearchUiEvent.Paginate -> {
                _searchState.update {
                    it.copy(
                        searchPage = it.searchPage + 1
                    )
                }
                loadSearchList()
            }
        }
    }

    private fun loadSearchList() {
        viewModelScope.launch {
            searchRepository.getSearchList(
                query = searchState.value.searchQuery,
                page = searchState.value.searchPage
            ).collect { result ->

                when(result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        _searchState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { newSearchList ->
                            _searchState.update {
                                it.copy(
                                    searchMediaList = it.searchMediaList + newSearchList
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



















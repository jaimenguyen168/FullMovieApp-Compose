package com.example.fullmovieapp_compose.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullmovieapp_compose.auth.domain.repo.AuthRepository
import com.example.fullmovieapp_compose.auth.util.AuthResult
import com.example.fullmovieapp_compose.profile.domain.repo.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
): ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    private val _logoutResultChannel = Channel<AuthResult<Unit>>()
    val logoutResultChannel = _logoutResultChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _profileState.update {
                it.copy(
                    name = profileRepository.getName(),
                    email = profileRepository.getEmail()
                )
            }
        }
    }

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.Logout -> {
                viewModelScope.launch {
                    _logoutResultChannel.send(
                        authRepository.logout()
                    )
                }
            }
        }
    }
}














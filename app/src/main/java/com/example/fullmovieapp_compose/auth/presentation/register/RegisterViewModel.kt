package com.example.fullmovieapp_compose.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullmovieapp_compose.auth.domain.repo.AuthRepository
import com.example.fullmovieapp_compose.auth.domain.usecase.FormValidatorUseCase
import com.example.fullmovieapp_compose.auth.util.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val formValidator: FormValidatorUseCase
): ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _authResultChannel = Channel<AuthResult<Unit>>()
    val authResultChannel = _authResultChannel.receiveAsFlow()

    private val _invalidCredentialsChannel = Channel<Boolean>()
    val invalidCredentialsChannel = _invalidCredentialsChannel.receiveAsFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.OnEmailChanged -> {
                _registerState.update {
                    it.copy(email = event.newEmail)
                }
            }
            is RegisterUiEvent.OnNameChanged -> {
                _registerState.update {
                    it.copy(name = event.newName)
                }
            }
            is RegisterUiEvent.OnPasswordChanged -> {
                _registerState.update {
                    it.copy(password = event.newPassword)
                }
            }
            RegisterUiEvent.Register -> {
                val isNameValid = formValidator.validateName.invoke(registerState.value.name)
                val isEmailValid = formValidator.validateEmail.invoke(registerState.value.email)
                val isPasswordValid = formValidator.validatePassword.invoke(registerState.value.password)

                if (isNameValid && isEmailValid && isPasswordValid) {
                    register()
                } else {
                    viewModelScope.launch {
                        _invalidCredentialsChannel.send(true)
                    }
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _registerState.update {
                it.copy(isLoading = true)
            }

            val result = authRepository.register(
                name = registerState.value.name,
                email = registerState.value.email,
                password = registerState.value.password
            )
            _authResultChannel.send(result)

            _registerState.update {
                it.copy(isLoading = false)
            }
        }
    }
}

































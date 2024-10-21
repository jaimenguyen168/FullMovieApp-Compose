package com.example.fullmovieapp_compose.auth.presentation.login

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val formValidator: FormValidatorUseCase
): ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _authResultChannel = Channel<AuthResult<Unit>>()
    val authResultChannel = _authResultChannel.receiveAsFlow()

    private val _invalidCredentialsChannel = Channel<Boolean>()
    val invalidCredentialsChannel = _invalidCredentialsChannel.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnEmailChanged -> {
                _loginState.update {
                    it.copy(email = event.newEmail)
                }
            }
            is LoginUiEvent.OnPasswordChanged -> {
                _loginState.update {
                    it.copy(password = event.newPassword)
                }
            }
            LoginUiEvent.Login -> {
                val isEmailValid = formValidator.validateEmail.invoke(loginState.value.email)
                val isPasswordValid = formValidator.validatePassword.invoke(loginState.value.password)

                if (isEmailValid && isPasswordValid) {
                    login()
                } else {
                    viewModelScope.launch {
                        _invalidCredentialsChannel.send(true)
                    }
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _loginState.update {
                it.copy(isLoading = true)
            }

            val result = authRepository.login(
                email = loginState.value.email,
                password = loginState.value.password
            )
            _authResultChannel.send(result)
        }
    }
}

































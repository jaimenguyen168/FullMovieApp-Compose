package com.example.fullmovieapp_compose.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.fullmovieapp_compose.auth.util.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CoreScreen(
    authResultChannel: Flow<AuthResult<Unit>>,
    onAuthorized: () -> Unit,
    onUnauthorized: () -> Unit,
) {
    LaunchedEffect(true) {
        authResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    onAuthorized()
                }
                is AuthResult.SingedOut -> {
                    onUnauthorized()
                }
                is AuthResult.Unauthorized -> {
                    onUnauthorized()
                }
                is AuthResult.UnknownError -> {
                    onUnauthorized()
                }
            }
        }
    }
}
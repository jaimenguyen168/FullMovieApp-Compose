package com.example.fullmovieapp_compose.auth.domain.usecase

import android.util.Patterns

class ValidateEmailUseCase {
    operator fun invoke(email: String): Boolean = Patterns
        .EMAIL_ADDRESS
        .matcher(email)
        .matches()
}
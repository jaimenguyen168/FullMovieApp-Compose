package com.example.fullmovieapp_compose.auth.domain.usecase

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean =
        password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() }
}








package com.example.fullmovieapp_compose.auth.domain.usecase

class ValidateNameUseCase {
    operator fun invoke(name: String): Boolean =
        name.length in 4..50 &&
                name.all { it.isLetter() }

}
package com.example.fullmovieapp_compose.auth.domain.usecase

data class FormValidatorUseCase(
    val validateName: ValidateNameUseCase,
    val validateEmail: ValidateEmailUseCase,
    val validatePassword: ValidatePasswordUseCase
)

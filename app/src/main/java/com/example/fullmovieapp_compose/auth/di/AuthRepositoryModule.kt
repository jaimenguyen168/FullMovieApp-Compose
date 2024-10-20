package com.example.fullmovieapp_compose.auth.di

import com.example.fullmovieapp_compose.auth.domain.repo.AuthRepository
import com.example.fullmovieapp_compose.auth.data.repo.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

}
package com.example.fullmovieapp_compose.main.data.di

import com.example.fullmovieapp_compose.main.data.repo.MainRepositoryImpl
import com.example.fullmovieapp_compose.main.domain.repo.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository
}
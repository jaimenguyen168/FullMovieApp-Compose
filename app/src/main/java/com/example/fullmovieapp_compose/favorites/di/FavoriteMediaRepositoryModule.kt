package com.example.fullmovieapp_compose.favorites.di

import com.example.fullmovieapp_compose.favorites.data.repo.BackendFavoritesRepositoryImpl
import com.example.fullmovieapp_compose.favorites.data.repo.FavoriteMediaRepositoryImpl
import com.example.fullmovieapp_compose.favorites.domain.repo.BackendFavoritesRepository
import com.example.fullmovieapp_compose.favorites.domain.repo.FavoriteMediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteMediaRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteMediaRepository(
        favoriteMediaRepositoryImpl: FavoriteMediaRepositoryImpl
    ): FavoriteMediaRepository

    @Binds
    @Singleton
    abstract fun bindBackendFavoritesRepository(
        backendFavoritesRepositoryImpl: BackendFavoritesRepositoryImpl
    ): BackendFavoritesRepository
}
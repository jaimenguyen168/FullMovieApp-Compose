package com.example.fullmovieapp_compose.search.di

import com.example.fullmovieapp_compose.search.data.repo.SearchRepositoryImpl
import com.example.fullmovieapp_compose.search.domain.repo.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}
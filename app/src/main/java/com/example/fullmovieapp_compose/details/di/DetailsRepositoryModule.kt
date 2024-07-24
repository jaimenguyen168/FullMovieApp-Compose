package com.example.fullmovieapp_compose.details.di

import com.example.fullmovieapp_compose.details.data.repo.DetailsRepositoryImpl
import com.example.fullmovieapp_compose.details.data.repo.SimilarRepositoryImpl
import com.example.fullmovieapp_compose.details.data.repo.VideosRepositoryImpl
import com.example.fullmovieapp_compose.details.domain.repo.DetailsRepository
import com.example.fullmovieapp_compose.details.domain.repo.SimilarRepository
import com.example.fullmovieapp_compose.details.domain.repo.VideosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository

    @Binds
    @Singleton
    abstract fun bindVideosRepository(
        videosRepositoryImpl: VideosRepositoryImpl
    ): VideosRepository

    @Binds
    @Singleton
    abstract fun bindSimilarRepository(
        similarRepositoryImpl: SimilarRepositoryImpl
    ): SimilarRepository
}
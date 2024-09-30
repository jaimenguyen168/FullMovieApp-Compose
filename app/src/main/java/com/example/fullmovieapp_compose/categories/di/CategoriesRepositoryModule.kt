package com.example.fullmovieapp_compose.categories.di

import com.example.fullmovieapp_compose.categories.data.repo.CategoriesRepositoryImpl
import com.example.fullmovieapp_compose.categories.domain.repo.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository
}
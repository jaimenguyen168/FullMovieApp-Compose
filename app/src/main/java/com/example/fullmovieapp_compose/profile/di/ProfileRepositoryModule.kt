package com.example.fullmovieapp_compose.profile.di

import com.example.fullmovieapp_compose.profile.data.repo.ProfileRepositoryImpl
import com.example.fullmovieapp_compose.profile.domain.repo.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository
}
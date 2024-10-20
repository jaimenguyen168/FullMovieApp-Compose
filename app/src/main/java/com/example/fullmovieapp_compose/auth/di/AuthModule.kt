package com.example.fullmovieapp_compose.auth.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.fullmovieapp_compose.auth.data.remote.AuthApi
import com.example.fullmovieapp_compose.auth.domain.usecase.FormValidatorUseCase
import com.example.fullmovieapp_compose.auth.domain.usecase.ValidateEmailUseCase
import com.example.fullmovieapp_compose.auth.domain.usecase.ValidateNameUseCase
import com.example.fullmovieapp_compose.auth.domain.usecase.ValidatePasswordUseCase
import com.example.fullmovieapp_compose.util.BackendConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BackendConstants.BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        app: Application
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(app)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            app,
            "watchy_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideFormValidatorUseCase(): FormValidatorUseCase {
        return FormValidatorUseCase(
            validateName = ValidateNameUseCase(),
            validateEmail = ValidateEmailUseCase(),
            validatePassword = ValidatePasswordUseCase()
        )
    }
}


















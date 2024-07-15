package com.example.fullmovieapp_compose.main.data.di

import android.app.Application
import androidx.room.Room
import com.example.fullmovieapp_compose.main.data.local.MediaDatabase
import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideMediaApi(): MediaApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MediaApi.BASE_URL)
            .client(client)
            .build()
            .create()

    // inject room database
    @Provides
    @Singleton
    fun provideMediaDatabase(app: Application): MediaDatabase =
        Room.databaseBuilder(
            app,
            MediaDatabase::class.java,
            "media-db.db"
        ).build()
}
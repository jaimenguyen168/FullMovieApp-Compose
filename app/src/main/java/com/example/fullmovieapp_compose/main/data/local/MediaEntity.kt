package com.example.fullmovieapp_compose.main.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaEntity (
    @PrimaryKey
    val mediaId: Int,

    val adult: Boolean,
    val backdropPath: String,
    val genreIds: String,
    var mediaType: String,
    val originCountry: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    var category: String,

    val runtime: Int,
    val tagLine: String,

    val videoIds: String,
    val similarMediaIds: String
)
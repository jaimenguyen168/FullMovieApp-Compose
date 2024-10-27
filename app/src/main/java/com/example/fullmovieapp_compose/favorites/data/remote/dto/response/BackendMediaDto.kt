package com.example.fullmovieapp_compose.favorites.data.remote.dto.response

data class BackendMediaDto(
    val mediaId: Int,

    val isLiked: Boolean,
    val isBookmarked: Boolean,

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

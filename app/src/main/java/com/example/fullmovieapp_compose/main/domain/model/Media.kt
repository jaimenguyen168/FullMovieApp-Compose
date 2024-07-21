package com.example.fullmovieapp_compose.main.domain.model

data class Media(
    val mediaId: Int,

    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<String>,
    val mediaType: String,
    val originCountry: List<String>,
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

    // details
    val runtime: Int,
    val tagLine: String,

    // to load video list and similar media list using their ids
    val videoIds: List<String>,
    val similarMediaIds: List<Int>,
)

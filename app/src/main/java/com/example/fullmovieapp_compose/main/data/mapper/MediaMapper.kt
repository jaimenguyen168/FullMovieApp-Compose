package com.example.fullmovieapp_compose.main.data.mapper

import com.example.fullmovieapp_compose.main.data.local.MediaEntity
import com.example.fullmovieapp_compose.main.data.remote.dto.MediaDto
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.util.APIConstants.MOVIE

fun Media.toMediaEntity(): MediaEntity {
    return MediaEntity(
        mediaId = mediaId,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        originalTitle = originalTitle,
        category = category,

        runtime = runtime,
        tagLine = tagLine,
        videoIds = try {
            videoIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        similarMediaIds = try {
            similarMediaIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },

        isLiked = isLiked,
        isBookmarked = isBookmarked
    )
}

fun MediaEntity.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        originalTitle = originalTitle,
        category = category,

        runtime = runtime,
        tagLine = tagLine,
        videoIds = if (videoIds.isEmpty()) {
            emptyList()
        } else {
            try {
                videoIds.split(",").map { it }
            } catch (e: Exception) {
                emptyList()
            }
        },
        similarMediaIds = if (similarMediaIds.isEmpty()) {
            emptyList()
        } else {
            try {
                similarMediaIds.split(",").map { it.toInt() }
            } catch (e: Exception) {
                emptyList()
            }
        },

        isLiked = isLiked,
        isBookmarked = isBookmarked
    )
}

fun MediaDto.toMediaEntity(
    type: String,
    category: String,
    isLiked: Boolean = false,
    isBookmarked: Boolean = false
): MediaEntity {
    return MediaEntity(
        mediaId = id ?: 0,

        backdropPath = backdrop_path ?: "",
        originalLanguage = original_language ?: "",
        overview = overview ?: "",
        posterPath = poster_path ?: "",
        releaseDate = release_date ?: first_air_date ?: "",
        title = title ?: name ?: "",
        originalTitle = original_title ?: original_name ?: "",
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = try {
            genre_ids?.joinToString(",") ?: ""
        } catch (e: Exception) {
            ""
        },

        adult = adult ?: false,
        mediaType = type,
        category = category,
        originCountry = try {
            origin_country?.joinToString(",") ?: ""
        } catch (e: Exception) {
            ""
        },

        // default values since mediaDto doesn't have these values
        runtime = 0,
        tagLine = "",

        videoIds = "",
        similarMediaIds = "",

        isLiked = isLiked,
        isBookmarked = isBookmarked
    )
}



fun MediaDto.toMedia(
    category: String,
    isLiked: Boolean = false,
    isBookmarked: Boolean = false
) : Media {
    return Media(
        mediaId = id ?: 0,

        backdropPath = backdrop_path ?: "",
        originalLanguage = original_language ?: "",
        overview = overview ?: "",
        posterPath = poster_path ?: "",
        releaseDate = release_date ?: first_air_date ?: "",
        title = title ?: name ?: "",
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = if (genre_ids?.isEmpty() == true) {
            emptyList()
        } else {
            try {
                genre_ids?.map { it.toString() } ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        },
        adult = adult ?: false,
        mediaType = media_type ?: MOVIE,
        category = category,
        originCountry = origin_country ?: emptyList(),
        originalTitle = original_title ?: original_name ?: "",

        // default values since mediaDto doesn't have these values
        runtime = 0,
        tagLine = "",
        videoIds = emptyList(),
        similarMediaIds = emptyList(),

        isLiked = isLiked,
        isBookmarked = isBookmarked
    )
}
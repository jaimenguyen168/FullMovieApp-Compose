package com.example.fullmovieapp_compose.favorites.data.mapper

import com.example.fullmovieapp_compose.favorites.data.local.FavoriteMediaEntity
import com.example.fullmovieapp_compose.favorites.data.remote.dto.request.MediaRequest
import com.example.fullmovieapp_compose.favorites.data.remote.dto.response.BackendMediaDto
import com.example.fullmovieapp_compose.main.data.local.MediaEntity
import com.example.fullmovieapp_compose.main.domain.model.Media

fun FavoriteMediaEntity.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

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

        videoIds = try {
            videoIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        similarMediaIds = try {
            similarMediaIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    )
}

fun Media.toFavoriteMediaEntity(): FavoriteMediaEntity {
    return FavoriteMediaEntity(
        mediaId = mediaId,

        isSynced = false,
        isDeletedLocally = false,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

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
        }
    )
}

fun BackendMediaDto.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

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

        videoIds = try {
            videoIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        similarMediaIds = try {
            similarMediaIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    )
}

fun BackendMediaDto.toMediaEntity(): MediaEntity {
    return MediaEntity(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,

        runtime = runtime,
        tagLine = tagLine,

        videoIds = videoIds,
        similarMediaIds = similarMediaIds
    )
}

fun BackendMediaDto.toFavoriteMediaEntity(): FavoriteMediaEntity {
    return FavoriteMediaEntity(
        mediaId = mediaId,

        isSynced = false,
        isDeletedLocally = false,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,

        runtime = runtime,
        tagLine = tagLine,

        videoIds = videoIds,
        similarMediaIds = similarMediaIds
    )
}

fun FavoriteMediaEntity.toMediaRequest(): MediaRequest {
    return MediaRequest(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,

        runtime = runtime,
        tagLine = tagLine,

        videoIds = videoIds,
        similarMediaIds = similarMediaIds
    )
}

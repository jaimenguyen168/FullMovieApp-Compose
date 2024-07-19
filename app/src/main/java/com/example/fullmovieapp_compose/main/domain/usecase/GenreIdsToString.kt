package com.example.fullmovieapp_compose.main.domain.usecase

import com.example.fullmovieapp_compose.util.GenreConstants.genres

object GenreIdsToString {
    fun genreIdsToString(genreIds: List<String>): String {
        return genreIds.map { id ->
            genres.find { genre ->
                genre.genreId.toString() == id
            }?.genreName
        }.joinToString(" - ")
    }
}
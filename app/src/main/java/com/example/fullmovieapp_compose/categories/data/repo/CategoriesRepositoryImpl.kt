package com.example.fullmovieapp_compose.categories.data.repo

import com.example.fullmovieapp_compose.categories.domain.repo.CategoriesRepository
import com.example.fullmovieapp_compose.main.data.local.MediaDatabase
import com.example.fullmovieapp_compose.main.data.mapper.toMedia
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.util.GenreConstants.genres
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    mediaDatabase: MediaDatabase
): CategoriesRepository {
    private val mediaDao = mediaDatabase.mediaDao

    private suspend fun getMediaListByGenreIds(
        genreIds: List<String>
    ): List<Media> {
        val mediaList = mediaDao.getAllMedia().map {
            it.toMedia()
        }
        return mediaList.filter { media ->
            media.genreIds.any { genreId ->
                genreId in genreIds
            }
        }.shuffled()
    }

    override suspend fun getActionAndAdventure(): List<Media> {
        val actionAndAdventure = genres.filter { genre ->
            genre.genreName in listOf(
                "Action",
                "Adventure",
                "Thriller",
                "Crime",
                "Western"
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(actionAndAdventure)
    }

    override suspend fun getDrama(): List<Media> {
        val dramas = genres.filter { genre ->
            genre.genreName in listOf(
                "Drama",
                "Comedy",
                "Family",
                "Romance",
                "Music"
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(dramas)
    }

    override suspend fun getComedy(): List<Media> {
        val comedies = genres.filter { genre ->
            genre.genreName in listOf(
                "Comedy",
                "Family",
                "Romance"
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(comedies)
    }

    override suspend fun getSciFiAndFantasy(): List<Media> {
        val sciFiAndFantasy = genres.filter { genre ->
            genre.genreName in listOf(
                "Fantasy",
                "Horror",
                "Thriller",
                "Crime",
                "Science Fiction",
                "Mystery",
            )
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(sciFiAndFantasy)
    }

    override suspend fun getAnimation(): List<Media> {
        val animations = genres.filter { genre ->
            genre.genreName == "Animation"
        }.map { it.genreId.toString() }

        return getMediaListByGenreIds(animations)
    }
}
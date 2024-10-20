package com.example.fullmovieapp_compose.util

object APIConstants {

    // Queries for API calls
    const val POPULAR = "popular"
    const val TRENDING = "trending"
    const val TRENDING_TIME = "day"
    const val ALL = "all"
    const val MOVIE = "movie"
    const val TV = "tv"

    // Tag for debugging
    const val GET_TAG = "--> GET https://api.themoviedb.org"
}

object Constants {
    const val actionAndAdventureList = "Action And Adventure"
    const val dramaList = "Drama"
    const val comedyList = "Comedy"
    const val sciFiAndFantasyList = "Sci-Fi And Fantasy"
    const val animationList = "Animation"
}

object BackendConstants {
    const val BACKEND_BASE_URL = "http://192.168.1.131:8081/"

    const val REGISTER = "register"
    const val LOGIN = "login"
    const val AUTHENTICATE = "authenticate"

    const val GET_LIKED_MEDIA_LIST = "get-liked-media-list/{email}"
    const val GET_BOOKMARKED_MEDIA_LIST = "get-bookmarked-media-list/{email}"
    const val GET_MEDIA_BY_ID = "get-media-by-id/{mediaId}/{email}"
    const val UPSERT_MEDIA_TO_USER = "upsert-media-to-user"
    const val DELETE_MEDIA_FROM_USER = "delete-media-from-user"
}
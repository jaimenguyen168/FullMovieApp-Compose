package com.example.fullmovieapp_compose.util

sealed interface Screen {
    @kotlinx.serialization.Serializable
    data object MainScreen: Screen

    @kotlinx.serialization.Serializable
    data object TrendingScreen: Screen

    @kotlinx.serialization.Serializable
    data object TVScreen: Screen

    @kotlinx.serialization.Serializable
    data object MovieScreen: Screen

}
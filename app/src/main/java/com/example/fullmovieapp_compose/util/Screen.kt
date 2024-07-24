package com.example.fullmovieapp_compose.util
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable data object MainScreen: Screen
    @Serializable data object TrendingScreen: Screen
    @Serializable data object TVScreen: Screen
    @Serializable data object MovieScreen: Screen

    // Details Route
    @Serializable
    data class CoreDetails(val mediaId: Int): Screen
    @Serializable data object Details: Screen
    @Serializable data object WatchVideo: Screen
    @Serializable data object Similar: Screen
}
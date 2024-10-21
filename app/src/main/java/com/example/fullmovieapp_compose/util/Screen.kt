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

    // Search Route
    @Serializable data object Search: Screen

    // Favorites Route
    @Serializable data object CoreFavorites: Screen
    @Serializable data object Favorites: Screen
    @Serializable data object LikedList: Screen
    @Serializable data object BookmarkedList: Screen

    // Categories Route
    @Serializable data object CoreCategories: Screen
    @Serializable data object Categories: Screen
    @Serializable
    data class CategoriesList(val category: String): Screen

    // Core & Auth
    @Serializable data object Core: Screen
    @Serializable data object Login: Screen
    @Serializable data object Register: Screen

}
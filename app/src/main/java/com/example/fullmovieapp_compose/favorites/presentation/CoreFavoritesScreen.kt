package com.example.fullmovieapp_compose.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fullmovieapp_compose.favorites.presentation.favorite_list.FavoritesListScreen
import com.example.fullmovieapp_compose.favorites.presentation.favorites.FavoritesScreen
import com.example.fullmovieapp_compose.util.Screen

@Composable
fun CoreFavoritesScreen(
    mainNavController: NavController
) {
    val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
    val  favoritesState by favoritesViewModel.favoritesState.collectAsState()

    val favoriteNavController = rememberNavController()
    
    NavHost(
        navController = favoriteNavController,
        startDestination = Screen.Favorites
    ) {
        composable<Screen.Favorites> {
            FavoritesScreen(
                mainNavController = mainNavController,
                favoritesNavController = favoriteNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }

        composable<Screen.LikedList> {
            FavoritesListScreen(
                route = Screen.LikedList,
                favoritesNavController = favoriteNavController,
                mainNavController = mainNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }

        composable<Screen.BookmarkedList> {
            FavoritesListScreen(
                route = Screen.BookmarkedList,
                favoritesNavController = favoriteNavController,
                mainNavController = mainNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }
    }
}



















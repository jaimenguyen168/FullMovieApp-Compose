package com.example.fullmovieapp_compose.categories.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.fullmovieapp_compose.categories.presentation.category.CategoryScreen
import com.example.fullmovieapp_compose.categories.presentation.category_list.CategoriesListScreen
import com.example.fullmovieapp_compose.util.Screen

@Composable
fun CoreCategoriesScreen(
    mainNavController: NavController,
) {
    val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
    val categoriesState = categoriesViewModel.categoriesState.collectAsState().value

    val categoriesNavController = rememberNavController()

    NavHost(
        navController = categoriesNavController,
        startDestination = Screen.CoreCategories
    ) {
        composable<Screen.CoreCategories> {
            CategoryScreen(
                categoriesNavController = categoriesNavController,
                categoriesState = categoriesState
            )
        }

        composable<Screen.CategoriesList> {
            val args = it.toRoute<Screen.CategoriesList>()

            CategoriesListScreen(
                category = args.category,
                mainNavController = mainNavController,
                categoriesState = categoriesState
            )
        }
    }
}
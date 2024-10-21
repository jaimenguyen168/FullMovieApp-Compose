package com.example.fullmovieapp_compose.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.fullmovieapp_compose.auth.presentation.login.LogInScreen
import com.example.fullmovieapp_compose.auth.presentation.register.RegisterScreen
import com.example.fullmovieapp_compose.categories.presentation.CoreCategoriesScreen
import com.example.fullmovieapp_compose.details.presentation.CoreDetailsScreen
import com.example.fullmovieapp_compose.favorites.presentation.CoreFavoritesScreen
import com.example.fullmovieapp_compose.main.presentation.MainScreen
import com.example.fullmovieapp_compose.main.presentation.MainUiEvent
import com.example.fullmovieapp_compose.main.presentation.MainViewModel
import com.example.fullmovieapp_compose.main.presentation.main_media_list.MainMediaListScreen
import com.example.fullmovieapp_compose.search.presentation.SearchListScreen
import com.example.fullmovieapp_compose.ui.theme.FullMovieAppComposeTheme
import com.example.fullmovieapp_compose.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val coreViewModel by viewModels<CoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullMovieAppComposeTheme {

                // change the status bar color
                BarColor()

                installSplashScreen().apply {
                    setKeepOnScreenCondition(
                        condition = { coreViewModel.isLoading.value }
                    )
                }

                // A surface container using the 'background' color from the theme
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNav()
                }
            }
        }
    }

    @Composable
    private fun MainNav() {
        val mainViewModel = hiltViewModel<MainViewModel>()
        val mainState = mainViewModel.mainState.collectAsState().value
        val mainNavController = rememberNavController()

        NavHost(
            navController = mainNavController,
            startDestination = Screen.Core
        ) {
            composable<Screen.Core> {
                CoreScreen(
                    authResultChannel = coreViewModel.authResultChannel,
                    onAuthorized = {
                        mainViewModel.onEvent(MainUiEvent.LoadAll)
                        mainNavController.popBackStack()
                        mainNavController.navigate(Screen.MainScreen)
                    },
                    onUnauthorized = {
                        mainNavController.popBackStack()
                        mainNavController.navigate(Screen.Login)
                    }
                )
            }

            composable<Screen.Login> {
                LogInScreen(
                    onAuthorized = {
                        mainViewModel.onEvent(MainUiEvent.LoadAll)
                        mainNavController.popBackStack()
                        mainNavController.navigate(Screen.MainScreen)
                    },
                    onRegisterClick = {
                        mainNavController.popBackStack()
                        mainNavController.navigate(Screen.Register)
                    }
                )
            }

            composable<Screen.Register> {
                RegisterScreen (
                    onAuthorized = {
                        mainViewModel.onEvent(MainUiEvent.LoadAll)
                        mainNavController.popBackStack()
                        mainNavController.navigate(Screen.MainScreen)
                    },
                    onSignInClick = {
                        mainNavController.popBackStack()
                        mainNavController.navigate(Screen.Login)
                    }
                )
            }

            composable<Screen.MainScreen> {
                MainScreen(
                    mainState = mainState,
                    mainNavController = mainNavController,
                    onEvent = mainViewModel::onEvent
                )
            }

            composable<Screen.TrendingScreen> {
                MainMediaListScreen(
                    route = Screen.TrendingScreen,
                    mainState = mainState,
                    mainNavController = mainNavController,
                    onEvent = mainViewModel::onEvent
                )
            }

            composable<Screen.TVScreen> {
                MainMediaListScreen(
                    route = Screen.TVScreen,
                    mainState = mainState,
                    mainNavController = mainNavController,
                    onEvent = mainViewModel::onEvent
                )
            }

            composable<Screen.MovieScreen> {
                MainMediaListScreen(
                    route = Screen.MovieScreen,
                    mainState = mainState,
                    mainNavController = mainNavController,
                    onEvent = mainViewModel::onEvent
                )
            }

            // passing arguments using type-safe navigation
            composable<Screen.CoreDetails> {
                val args = it.toRoute<Screen.CoreDetails>()
                CoreDetailsScreen(
                    mediaId = args.mediaId,
                    mainNavController = mainNavController,
                )
            }

            composable<Screen.Search> {
                SearchListScreen(
                    mainNavController = mainNavController
                )
            }

            composable<Screen.CoreFavorites> {
                CoreFavoritesScreen(
                    mainNavController = mainNavController
                )
            }

            composable<Screen.CoreCategories> {
                CoreCategoriesScreen(
                    mainNavController = mainNavController
                )
            }
        }
    }

    @Composable
    private fun BarColor() {
        val systemUiController = rememberSystemUiController()
        val color = MaterialTheme.colorScheme.background

        LaunchedEffect(color) {
            systemUiController.setSystemBarsColor(color)
        }
    }
}

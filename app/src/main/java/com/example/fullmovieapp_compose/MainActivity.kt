package com.example.fullmovieapp_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.fullmovieapp_compose.details.presentation.CoreDetailsScreen
import com.example.fullmovieapp_compose.main.presentation.MainScreen
import com.example.fullmovieapp_compose.main.presentation.MainViewModel
import com.example.fullmovieapp_compose.main.presentation.main_media_list.MainMediaListScreen
import com.example.fullmovieapp_compose.ui.theme.FullMovieAppComposeTheme
import com.example.fullmovieapp_compose.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            FullMovieAppComposeTheme {

                // change the status bar color
                BarColor()

                // A surface container using the 'background' color from the theme
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel = hiltViewModel<MainViewModel>()
                    val mainState = mainViewModel.mainState.collectAsState().value
                    val mainNavController = rememberNavController()

                    NavHost(
                        navController = mainNavController,
                        startDestination = Screen.MainScreen
                    ) {
                        composable<Screen.MainScreen> {
                            MainScreen(
                                mainState = mainState,
                                mainNavController = mainNavController,
                                onEvent = mainViewModel::event
                            )
                        }

                        composable<Screen.TrendingScreen> {
                            MainMediaListScreen(
                                route = Screen.TrendingScreen,
                                mainState = mainState,
                                mainNavController = mainNavController,
                                onEvent = mainViewModel::event
                            )
                        }

                        composable<Screen.TVScreen> {
                            MainMediaListScreen(
                                route = Screen.TVScreen,
                                mainState = mainState,
                                mainNavController = mainNavController,
                                onEvent = mainViewModel::event
                            )
                        }

                        composable<Screen.MovieScreen> {
                            MainMediaListScreen(
                                route = Screen.MovieScreen,
                                mainState = mainState,
                                mainNavController = mainNavController,
                                onEvent = mainViewModel::event
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
                    }
                }
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

package com.example.fullmovieapp_compose.details.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.presentation.details.DetailsScreen
import com.example.fullmovieapp_compose.details.presentation.details.DetailsUiEvent
import com.example.fullmovieapp_compose.details.presentation.details.DetailsViewModel
import com.example.fullmovieapp_compose.details.presentation.similar_list.SimilarListScreen
import com.example.fullmovieapp_compose.details.presentation.watch_video.WatchVideoScreen
import com.example.fullmovieapp_compose.util.Screen

@Composable
fun CoreDetailsScreen(
    mediaId: Int,
    mainNavController: NavController,
) {
    val detailsViewModel: DetailsViewModel = hiltViewModel()
    val detailsState = detailsViewModel.detailsState.collectAsState().value

    // Fire off the event to load the media item
    LaunchedEffect(true) {
        detailsViewModel.onEvent(
            DetailsUiEvent.StartLoading(mediaId)
        )
    }

    // Another navigation inside the DetailsScreen
    val detailScreenNavController = rememberNavController()

    NavHost(
        navController = detailScreenNavController,
        startDestination = Screen.Details
    ) {
        composable<Screen.Details> {
            DetailsScreen(
                detailsState = detailsState,
                mainNavController = mainNavController,
                detailsNavController = detailScreenNavController,
                onEvent = detailsViewModel::onEvent
            )
        }

        composable<Screen.WatchVideo> {
            WatchVideoScreen(
                lifecycleOwner = LocalLifecycleOwner.current,
                detailsState = detailsState
            )
        }

        composable<Screen.Similar> {
            SimilarListScreen(
                detailsState = detailsState,
                mainNavController = mainNavController
            )
        }
    }

    val context = LocalContext.current

    LaunchedEffect(true) {
        detailsViewModel.navigateToWatchVideoChannel.collect { shouldNavigate ->
            if (shouldNavigate) {
                detailScreenNavController.navigate(Screen.WatchVideo)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.video_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}


















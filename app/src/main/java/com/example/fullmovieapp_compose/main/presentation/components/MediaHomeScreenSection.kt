package com.example.fullmovieapp_compose.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.main.presentation.MainState
import com.example.fullmovieapp_compose.ui.components.MediaItemImage
import com.example.fullmovieapp_compose.ui.theme.Radius
import com.example.fullmovieapp_compose.util.APIConstants.MOVIE
import com.example.fullmovieapp_compose.util.APIConstants.TRENDING
import com.example.fullmovieapp_compose.util.APIConstants.TV
import com.example.fullmovieapp_compose.util.Screen

@Composable
fun MediaHomeScreenSection(
    route: Screen,
    mainState: MainState,
    mainNavController: NavController
) {
    var mediaList = emptyList<Media>()
    var title = ""

    when (route) {
        Screen.MainScreen -> Unit
        Screen.MovieScreen -> {
            mediaList = mainState.movieList.take(10)
            title = stringResource(R.string.movie)
        }
        Screen.TVScreen -> {
            mediaList = mainState.tvList.take(10)
            title = stringResource(R.string.tv_series)
        }
        Screen.TrendingScreen -> {
            mediaList = mainState.trendingList.take(10)
            title = stringResource(R.string.trending_now)
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )

            if (mediaList.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.see_all),
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        mainNavController.navigate(route)
                    }
                )
            }
        }

        if (mediaList.isEmpty()) {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                repeat(6) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(Radius))
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        } else {
            LazyRow {
                items(mediaList.size) { index ->

                    var paddingEnd = 0.dp

                    if (index == mediaList.size - 1) {
                        paddingEnd = 16.dp
                    }

                    MediaItemImage(
                        mediaItem = mediaList[index],
                        mainNavController = mainNavController,
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp)
                            .padding(start = 16.dp, end = paddingEnd)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaHomeScreenSectionPreview() {
    MediaHomeScreenSection(
        route = Screen.TrendingScreen,
        mainState = MainState(),
        mainNavController = NavController(LocalContext.current)
    )
}
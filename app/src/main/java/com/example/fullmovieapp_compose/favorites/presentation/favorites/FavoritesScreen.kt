package com.example.fullmovieapp_compose.favorites.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.favorites.presentation.FavoritesState
import com.example.fullmovieapp_compose.favorites.presentation.FavoritesUiEvent
import com.example.fullmovieapp_compose.ui.components.AutoSwipeSection
import com.example.fullmovieapp_compose.ui.theme.MediumRadius
import com.example.fullmovieapp_compose.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(
    mainNavController: NavController,
    favoritesNavController: NavController,
    favoritesState: FavoritesState,
    onEvent: (FavoritesUiEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun onRefresh() = scope.launch {
        refreshing = true
        onEvent(FavoritesUiEvent.Refresh)
        delay(1500)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = ::onRefresh
    )

    Scaffold(
        topBar = {
            Text(
                text = stringResource(id = R.string.favorite_and_bookmark),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                if (favoritesState.likedList.isEmpty()) {
                    NoItemRowList(title = stringResource(R.string.favorites_heart))
                } else {
                    AutoSwipeSection(
                        title = stringResource(id = R.string.favorites_heart),
                        mainNavController = mainNavController,
                        mediaList = favoritesState.likedList,
                        showSeeAll = true,
                        route = Screen.LikedList,
                        navController = favoritesNavController
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                if (favoritesState.bookmarkedList.isEmpty()) {
                    NoItemRowList(title = stringResource(R.string.bookmarks_fire))
                } else {
                    AutoSwipeSection(
                        title = stringResource(id = R.string.bookmarks_fire),
                        mainNavController = mainNavController,
                        mediaList = favoritesState.bookmarkedList,
                        showSeeAll = true,
                        route = Screen.BookmarkedList,
                        navController = favoritesNavController
                    )
                }
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun NoItemRowList(
    title: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp)
        )

        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(0.9f)
                .padding(top = 20.dp, bottom = 12.dp)
                .clip(RoundedCornerShape(MediumRadius))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(
        mainNavController = rememberNavController(),
        favoritesNavController = rememberNavController(),
        favoritesState = FavoritesState()
    ) {
    }
}
























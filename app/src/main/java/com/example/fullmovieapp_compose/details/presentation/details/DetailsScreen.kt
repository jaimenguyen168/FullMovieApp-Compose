package com.example.fullmovieapp_compose.details.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.presentation.component.FavoriteSection
import com.example.fullmovieapp_compose.details.presentation.component.InfoSection
import com.example.fullmovieapp_compose.details.presentation.component.OverviewSection
import com.example.fullmovieapp_compose.details.presentation.component.PosterSection
import com.example.fullmovieapp_compose.details.presentation.component.SimilarSection
import com.example.fullmovieapp_compose.details.presentation.component.VideoSection
import com.example.fullmovieapp_compose.util.backgroundColor
import com.example.fullmovieapp_compose.util.onBackgroundColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsScreen(
    detailsState: DetailsState,
    mainNavController: NavController,
    detailsNavController: NavController,
    onEvent: (DetailsUiEvent) -> Unit
) {
    if (detailsState.media == null) {
        SomethingWrongScreen()
    } else {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }

        fun onRefresh() = refreshScope.launch {
            refreshing = true
            delay(1500)
//            onEvent(DetailsUiEvent.Refresh)
            refreshing = false
        }

        val refreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = ::onRefresh
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    VideoSection(
                        media = detailsState.media,
                        onEvent = onEvent
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 180.dp)
                    ) {
                        PosterSection(
                            media = detailsState.media
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoSection(
                            media = detailsState.media,
                            readableTime = detailsState.readableTime
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OverviewSection(
                    media = detailsState.media,
                )

                SimilarSection(
                    media = detailsState.media,
                    detailsState = detailsState,
                    mainNavController = mainNavController,
                    detailsNavController = detailsNavController,
                )

                Spacer(modifier = Modifier.height(100.dp))
            }

            FavoriteSection(
                detailsState = detailsState,
                onEvent = onEvent,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun SomethingWrongScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor())
    ) {
        Text(
            text = stringResource(R.string.something_went_wrong),
            fontSize = 22.sp,
            color = onBackgroundColor(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}




















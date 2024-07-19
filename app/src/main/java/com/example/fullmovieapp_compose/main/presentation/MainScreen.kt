package com.example.fullmovieapp_compose.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.main.presentation.components.MediaHomeScreenSection
import com.example.fullmovieapp_compose.ui.components.AutoSwipeSection
import com.example.fullmovieapp_compose.ui.components.NonFocusedTopBar
import com.example.fullmovieapp_compose.ui.theme.BigRadius
import com.example.fullmovieapp_compose.ui.theme.MediumRadius
import com.example.fullmovieapp_compose.util.Screen
import com.example.fullmovieapp_compose.util.Screen.MainScreen
import com.example.fullmovieapp_compose.util.onBackgroundColor
import com.example.fullmovieapp_compose.util.surfaceVariantColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    mainState: MainState,
    mainNavController: NavController,
    onEvent: (MainUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
//                mainNavController.navigate()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Category,
                    contentDescription = stringResource(R.string.categories)
                )
            }
        }
    ) { paddingValues ->
        val padding = paddingValues

        val toolbarHeightPx = with(LocalDensity.current) {
            BigRadius.roundToPx().toFloat()
        }
        val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset, source: NestedScrollSource
                ): Offset {
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx.floatValue + delta
                    toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeightPx, 0f)
                    return Offset.Zero
                }
            }
        }

        val scope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }

        fun onRefresh() = scope.launch {
            refreshing = true
            onEvent(MainUiEvent.Refresh(MainScreen))
            delay(1500)
            refreshing = false
        }

        val refreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = ::onRefresh
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .pullRefresh(refreshState)
//                .nestedScroll()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = BigRadius)
            ) {
                MediaHomeScreenSection(
                    route = Screen.TrendingScreen,
                    mainState = mainState,
                    mainNavController = mainNavController
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (mainState.specialList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.special),
                        color = onBackgroundColor(),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(
                            vertical = 16.dp,
                            horizontal = 32.dp
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(MediumRadius))
                            .background(surfaceVariantColor())
                    )
                } else {
                    AutoSwipeSection(
                        title = stringResource(R.string.special),
                        mainNavController = mainNavController,
                        mediaList = mainState.specialList
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                MediaHomeScreenSection(
                    route = Screen.TVScreen,
                    mainState = mainState,
                    mainNavController = mainNavController
                )

                Spacer(modifier = Modifier.height(16.dp))

                MediaHomeScreenSection(
                    route = Screen.MovieScreen,
                    mainState = mainState,
                    mainNavController = mainNavController
                )

                Spacer(modifier = Modifier.height(90.dp))
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = BigRadius - 8.dp)
            )
        }
        
        NonFocusedTopBar(
            username = mainState.name,
            mainNavController = mainNavController,
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        mainState = MainState(),
        mainNavController = NavController(LocalContext.current),
        onEvent = {}
    )
}









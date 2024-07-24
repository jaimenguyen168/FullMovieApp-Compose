package com.example.fullmovieapp_compose.details.presentation.similar_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fullmovieapp_compose.details.presentation.details.DetailsState
import com.example.fullmovieapp_compose.ui.components.MediaItemImageAndTitle
import com.example.fullmovieapp_compose.ui.components.NonFocusedTopBar
import com.example.fullmovieapp_compose.ui.theme.HugeRadius
import kotlin.math.roundToInt

@Composable
fun SimilarListScreen(
    detailsState: DetailsState,
    mainNavController: NavController,
) {
    val toolbarHeightPx = with(LocalDensity.current) {
        HugeRadius.roundToPx().toFloat()
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

    val title = "Similar to ${detailsState.media?.title}"

//    val scope = rememberCoroutineScope()
//    var refreshing by remember { mutableStateOf(false) }
//
//    fun onRefresh() = scope.launch {
//        refreshing = true
//        onEvent(MainUiEvent.Refresh(route))
//        delay(1500)
//        refreshing = false
//    }
//
//    val refreshState = rememberPullRefreshState(
//        refreshing = refreshing,
//        onRefresh = ::onRefresh
//    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
//            .pullRefresh(refreshState)
    ) {
        val lazyState = rememberLazyGridState()

        LazyVerticalGrid(
            state = lazyState,
            columns = GridCells.Adaptive(190.dp),
            contentPadding = PaddingValues(top = HugeRadius)
        ) {
            items(detailsState.similarMediaList.size) { index ->
                MediaItemImageAndTitle(
                    mediaItem = detailsState.similarMediaList[index],
                    mainNavController = mainNavController
                )

                // paginate only when at the end of the list and when it's not loading
//                if (index >= mediaList.size - 1 && !mainState.isLoading) {
//                    onEvent(MainUiEvent.Paginate(route))
//                }
            }
        }

        NonFocusedTopBar(
            mainNavController = mainNavController,
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
            title = title
        )

//        PullRefreshIndicator(
//            refreshing = refreshing,
//            state = refreshState,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = BigRadius - 8.dp)
//        )
    }
}

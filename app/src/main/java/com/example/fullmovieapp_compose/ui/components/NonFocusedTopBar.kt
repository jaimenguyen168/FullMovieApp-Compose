package com.example.fullmovieapp_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fullmovieapp_compose.ui.theme.BigRadius
import com.example.fullmovieapp_compose.ui.theme.HugeRadius
import com.example.fullmovieapp_compose.util.backgroundColor
import com.example.fullmovieapp_compose.util.onBackgroundColor

@Composable
fun NonFocusedTopBar(
    username: String = "",
    title: String = "",
    mainNavController: NavController,
    toolbarOffsetHeightPx: Int
) {
    Box(
        modifier = Modifier
            .height(if (title.isNotEmpty()) HugeRadius else BigRadius)
            .offset {
                IntOffset(x = 0, y = toolbarOffsetHeightPx)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (title.isNotEmpty()) backgroundColor()
                    else Color.Transparent
                )
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NonFocusedSearchBar(
                username = username,
                mainNavController = mainNavController
            )

            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    color = onBackgroundColor(),
                    fontSize = 19.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    NonFocusedTopBar(
        username = "Jaime",
        title = "Trending",
        mainNavController = NavController(LocalContext.current),
        toolbarOffsetHeightPx = 16
    )
}


















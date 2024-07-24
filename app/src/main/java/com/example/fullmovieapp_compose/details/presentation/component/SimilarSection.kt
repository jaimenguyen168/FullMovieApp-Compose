package com.example.fullmovieapp_compose.details.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.presentation.details.DetailsState
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.ui.components.MediaItemImage
import com.example.fullmovieapp_compose.util.Screen
import com.example.fullmovieapp_compose.util.onBackgroundColor

@Composable
fun SimilarSection(
    media: Media,
    detailsState: DetailsState,
    mainNavController: NavController,
    detailsNavController: NavController,
) {
    val similarMediaList = detailsState.similarMediaList.take(10)

    if (similarMediaList.isNotEmpty()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 22.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.similar),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = onBackgroundColor()
                )

                Text(
                    text = stringResource(R.string.see_all),
                    fontSize = 14.sp,
                    color = onBackgroundColor(),
                    modifier = Modifier.clickable {
                        detailsNavController.navigate(Screen.Similar)
                    }
                )
            }

            LazyRow {
                items(similarMediaList.size) { index ->
                    val paddingEnd = if (index == similarMediaList.size - 1) { 16.dp } else { 0.dp }

                    MediaItemImage(
                        mediaItem = similarMediaList[index],
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
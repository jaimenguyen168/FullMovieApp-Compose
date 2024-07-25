package com.example.fullmovieapp_compose.search.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.main.domain.usecase.GenreIdsToString
import com.example.fullmovieapp_compose.search.presentation.SearchUiEvent
import com.example.fullmovieapp_compose.ui.components.AverageColor
import com.example.fullmovieapp_compose.ui.components.RatingBar
import com.example.fullmovieapp_compose.ui.theme.Radius
import com.example.fullmovieapp_compose.ui.theme.RadiusContainer
import com.example.fullmovieapp_compose.util.Screen
import com.example.fullmovieapp_compose.util.primaryContainerColor
import com.example.fullmovieapp_compose.util.secondaryContainerColor

@Composable
fun SearchItem(
    mediaItem: Media,
    onEvent: (SearchUiEvent) -> Unit,
) {
    val imageUri = "${MediaApi.IMAGE_URL}${mediaItem.posterPath}"

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val defaultColor = primaryContainerColor()
    var averageColor by remember { mutableStateOf(defaultColor) }

    Column(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(RadiusContainer))
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        secondaryContainerColor(),
                        averageColor
                    )
                )
            )
            .clickable {
                onEvent(
                    SearchUiEvent.OnSearchItemClick(mediaItem)
                )
            }
    ) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(RadiusContainer))
                .padding(6.dp)
        ) {
            when (imageState) {
                is AsyncImagePainter.State.Success -> {
                    val bitmap = imageState.result.drawable.toBitmap()

                    averageColor = AverageColor(
                        imageBitmap = bitmap.asImageBitmap()
                    )

                    Image(
                        painter = imageState.painter,
                        contentDescription = mediaItem.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(Radius))
                    )
                }
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                    )
                }
                else -> { // AsyncImagePainter.State.Error
                    Icon(
                        imageVector = Icons.Rounded.ImageNotSupported,
                        contentDescription = mediaItem.title,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        
        Text(
            text = mediaItem.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )

        Text(
            text = GenreIdsToString.genreIdsToString(mediaItem.genreIds),
            fontSize = 13.sp,
            maxLines = 1,
            color = Color.White.copy(0.7f),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            RatingBar(
                starsModifier = Modifier.size(18.dp),
                rating = mediaItem.voteAverage / 2
            )

            Text(
                text = (mediaItem.voteAverage / 2).toString().take(3),
                fontSize = 14.sp,
                maxLines = 1,
                color = Color.White.copy(0.7f),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
        }
    }
}


















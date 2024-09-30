package com.example.fullmovieapp_compose.details.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.presentation.details.DetailsUiEvent
import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import com.example.fullmovieapp_compose.main.domain.model.Media

@Composable
fun VideoSection(
    media: Media,
    onEvent: (DetailsUiEvent) -> Unit
) {
    val imageUri = "${MediaApi.IMAGE_URL}${media.backdropPath}"
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                onEvent(DetailsUiEvent.NavigateToWatchVideo)
            },
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            MovieImage(
                imageState = imageState,
                description = stringResource(R.string.watch_trailer_of, media.title),
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .alpha(0.7f)
                    .background(Color.LightGray)
            )

            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = stringResource(R.string.watch_trailer),
                tint = Color.Black,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}




















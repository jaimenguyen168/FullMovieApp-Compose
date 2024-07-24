package com.example.fullmovieapp_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.ui.theme.Radius
import com.example.fullmovieapp_compose.util.Screen
import com.example.fullmovieapp_compose.util.onBackgroundColor
import com.example.fullmovieapp_compose.util.primaryColor

@Composable
fun MediaItemImage(
    modifier: Modifier = Modifier,
    mediaItem: Media,
    isPoster: Boolean = true,
    mainNavController: NavController
) {
    val imageUri = if (isPoster) {
        "${MediaApi.IMAGE_URL}${mediaItem.posterPath}"
    } else {
        "${MediaApi.IMAGE_URL}${mediaItem.backdropPath}"
    }

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Radius))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .clickable {
                mainNavController.navigate(
                    Screen.CoreDetails(mediaItem.mediaId)
                )
            }
    ) {
        when (imageState) {
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = imageState.painter,
                    contentDescription = mediaItem.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    color = primaryColor(),
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                )
            }
            else -> { // AsyncImagePainter.State.Error
                Icon(
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = mediaItem.title,
                    tint = onBackgroundColor(),
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}
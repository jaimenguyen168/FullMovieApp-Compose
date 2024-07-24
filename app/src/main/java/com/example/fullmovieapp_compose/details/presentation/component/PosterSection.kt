package com.example.fullmovieapp_compose.details.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.fullmovieapp_compose.ui.theme.SmallRadius

@Composable
fun PosterSection(
    media: Media,
) {
    val imageUri = "${MediaApi.IMAGE_URL}${media.posterPath}"
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(250.dp)
            .padding(start = 16.dp),
        shape = RoundedCornerShape(SmallRadius),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        MovieImage(
            imageState = imageState,
            description = stringResource(R.string.poster, media.title),
            icon = Icons.Rounded.ImageNotSupported,
        )
    }
}
















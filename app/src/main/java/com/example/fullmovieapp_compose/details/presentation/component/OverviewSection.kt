package com.example.fullmovieapp_compose.details.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.util.onBackgroundColor

@Composable
fun OverviewSection(
    media: Media
) {
    Column(
        modifier = Modifier.padding(horizontal = 22.dp)
    ) {
        if (media.tagLine.isNotEmpty()) {
            Text(
                text = "\"${media.tagLine}\"",
                color = onBackgroundColor(),
                fontSize = 17.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (media.overview.isNotEmpty()) {
            Text(
                text = stringResource(R.string.overview),
                color = onBackgroundColor(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = media.overview,
                color = onBackgroundColor(),
                fontSize = 16.sp,
            )
        }
    }
}
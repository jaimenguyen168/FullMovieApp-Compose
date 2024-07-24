package com.example.fullmovieapp_compose.details.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.main.domain.usecase.GenreIdsToString
import com.example.fullmovieapp_compose.ui.components.RatingBar
import com.example.fullmovieapp_compose.util.onBackgroundColor

@Composable
fun InfoSection(
    media: Media,
    readableTime: String
) {
    Column(
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = media.title,
            color = onBackgroundColor(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (media.voteAverage != 0.0) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    starsModifier = Modifier.size(18.dp),
                    rating = media.voteAverage.div(2),
                    starsColor = Color(0xFFf4cb45)
                )
                
                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = media.voteAverage.div(2).toString().take(3),
                    color = onBackgroundColor(),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = if (media.adult) {
                stringResource(R.string._18_plus)
            } else {
                stringResource(R.string._13_plus)
            },
            color = onBackgroundColor(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = onBackgroundColor(),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 6.dp, vertical = 0.5.dp)
        )

        Spacer(modifier = Modifier.height(7.dp))

        if (media.releaseDate.isNotEmpty()) {
            Text(
                text = media.releaseDate,
                color = onBackgroundColor(),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(7.dp))
        }

        if (media.genreIds.isNotEmpty()) {
            Text(
                text = GenreIdsToString.genreIdsToString(media.genreIds),
                color = onBackgroundColor(),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(7.dp))
        }

        if (readableTime.isNotEmpty()) {
            Text(
                text = readableTime,
                color = onBackgroundColor(),
                fontSize = 16.sp
            )
        }
    }
}
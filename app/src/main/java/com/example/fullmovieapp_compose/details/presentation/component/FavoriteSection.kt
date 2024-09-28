package com.example.fullmovieapp_compose.details.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.details.presentation.details.DetailsState
import com.example.fullmovieapp_compose.details.presentation.details.DetailsUiEvent

@Composable
fun FavoriteSection(
    detailsState: DetailsState,
    modifier: Modifier = Modifier,
    onEvent: (DetailsUiEvent) -> Unit
) {
    if (detailsState.showAlertDialog) {
        FavoriteAlertDialog(
            detailsState = detailsState,
            onEvent = onEvent
        )
    }

    detailsState.media?.let { media ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    onEvent(DetailsUiEvent.ShowOrHideAlertDialog(1))
                }
            ) {
                if (media.isLiked) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = stringResource(R.string.like)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.dislike)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(
                modifier = Modifier.fillMaxWidth(1f),
                onClick = {
                    onEvent(DetailsUiEvent.ShowOrHideAlertDialog(2))
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (media.isBookmarked) {
                        Icon(
                            imageVector = Icons.Rounded.Bookmark,
                            contentDescription = stringResource(R.string.bookmark)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = stringResource(R.string.unbookmark)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (media.isBookmarked) stringResource(R.string.unbookmark) else stringResource(R.string.bookmark),
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteAlertDialog(
    detailsState: DetailsState,
    onEvent: (DetailsUiEvent) -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = if (detailsState.alertType == 1) {
                    stringResource(R.string.remove_from_favorites)
                } else {
                    stringResource(R.string.remove_from_bookmarks)
                },
                fontSize = 17.sp
            )
        },
        confirmButton = {
            Button(onClick = {
                if (detailsState.alertType == 1) {
                    onEvent(DetailsUiEvent.LikeOrDislike)
                } else {
                    onEvent(DetailsUiEvent.BookmarkOrUnBookmark)
                }
            }) {
                Text(text = stringResource(R.string.yes))
            }
        },
        dismissButton = {
            Button(onClick = {
                onEvent(DetailsUiEvent.ShowOrHideAlertDialog())
            }) {
                Text(text = stringResource(R.string.no))
            }
        }
    )
}
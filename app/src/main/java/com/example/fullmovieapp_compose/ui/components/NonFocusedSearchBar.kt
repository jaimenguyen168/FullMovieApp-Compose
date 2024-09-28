package com.example.fullmovieapp_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.util.Screen
import com.example.fullmovieapp_compose.util.onBackgroundColor
import com.example.fullmovieapp_compose.util.onPrimaryColor
import com.example.fullmovieapp_compose.util.primaryColor
import com.example.fullmovieapp_compose.util.secondaryContainerColor

@Composable
fun NonFocusedSearchBar(
   username: String = "",
   mainNavController: NavController
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(secondaryContainerColor())
                .padding(start = 16.dp, end = 7.dp)
                .clickable {
                    mainNavController.navigate(Screen.Search)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(R.string.search_a_movie_or_tv_series),
                tint = onBackgroundColor().copy(0.5f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.search_a_movie_or_tv_series),
                color = onBackgroundColor().copy(0.5f),
                modifier = Modifier.weight(1f)
            )

            if (username.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .alpha(0.8f)
                        .background(primaryColor())
                        .clickable {

                        }
                ) {
                    Text(
                        text = username.take(1),
                        color = onPrimaryColor(),
                        fontSize = 19.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(47.dp)
                .clip(CircleShape)
                .background(secondaryContainerColor())
                .clickable {
                    mainNavController.navigate(Screen.CoreFavorites)
                }
        ) {
            Icon(
                imageVector = Icons.Rounded.Bookmark,
                contentDescription = stringResource(R.string.favorite_and_bookmark),
                tint = primaryColor(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}












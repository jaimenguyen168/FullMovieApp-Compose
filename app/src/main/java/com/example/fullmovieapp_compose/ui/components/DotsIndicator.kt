package com.example.fullmovieapp_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fullmovieapp_compose.util.primaryColor

@Composable
fun DotsIndicator(
    totalDots: Int,
    currentDot: Int
) {
    LazyRow(
        modifier = Modifier.wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(totalDots) { index ->
            if (index == currentDot) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(primaryColor())
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }

            if (index != currentDot - 1) {
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
    }
}
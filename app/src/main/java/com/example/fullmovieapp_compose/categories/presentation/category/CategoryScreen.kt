package com.example.fullmovieapp_compose.categories.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.categories.presentation.CategoriesState
import com.example.fullmovieapp_compose.main.data.remote.api.MediaApi
import com.example.fullmovieapp_compose.main.domain.model.Media
import com.example.fullmovieapp_compose.ui.theme.Radius
import com.example.fullmovieapp_compose.util.Constants
import com.example.fullmovieapp_compose.util.Screen
import com.example.fullmovieapp_compose.util.onBackgroundColor
import com.example.fullmovieapp_compose.util.primaryColor
import com.example.fullmovieapp_compose.util.surfaceVariantColor

@Composable
fun CategoryScreen(
    categoriesNavController: NavController,
    categoriesState: CategoriesState,
) {

    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.categories_title),
                fontSize = 20.sp,
                color = onBackgroundColor(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            if (categoriesState.actionAndAdventureList.isNotEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    CategoryImage(
                        category = Constants.actionAndAdventureList,
                        media = categoriesState.actionAndAdventureList[0],
                        categoriesNavController = categoriesNavController
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (categoriesState.dramaList.isNotEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    CategoryImage(
                        category = Constants.dramaList,
                        media = categoriesState.dramaList[0],
                        categoriesNavController = categoriesNavController
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (categoriesState.comedyList.isNotEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    CategoryImage(
                        category = Constants.comedyList,
                        media = categoriesState.comedyList[0],
                        categoriesNavController = categoriesNavController
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (categoriesState.sciFiAndFantasyList.isNotEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    CategoryImage(
                        category = Constants.sciFiAndFantasyList,
                        media = categoriesState.sciFiAndFantasyList[0],
                        categoriesNavController = categoriesNavController
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (categoriesState.animationList.isNotEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    CategoryImage(
                        category = Constants.animationList,
                        media = categoriesState.animationList[0],
                        categoriesNavController = categoriesNavController
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun CategoryImage(
    category: String,
    media: Media,
    categoriesNavController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(Radius))
            .background(surfaceVariantColor())
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data("${MediaApi.IMAGE_URL}${media.backdropPath}")
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = category,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor().copy(0.3f))
                .clickable {
                    categoriesNavController.navigate(
                        Screen.CategoriesList(
                            category = category
                        )
                    )
                }
        ) {
            Text(
                text = category,
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(x = 0.5f, y = 10.0f),
                        blurRadius = 3f
                    )
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}





















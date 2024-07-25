package com.example.fullmovieapp_compose.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.fullmovieapp_compose.search.presentation.SearchState
import com.example.fullmovieapp_compose.search.presentation.SearchUiEvent
import com.example.fullmovieapp_compose.ui.theme.BigRadius
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.util.onBackgroundColor
import com.example.fullmovieapp_compose.util.primaryColor
import com.example.fullmovieapp_compose.util.secondaryContainerColor


@Composable
fun FocusedTopBar(
    searchState: SearchState,
    toolbarOffsetHeightPx: Int,
    onEvent: (SearchUiEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .height(BigRadius)
            .offset {
                IntOffset(0, toolbarOffsetHeightPx)
            }
    ) {
        SearchBar(
            searchState = searchState,
            onEvent = onEvent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchBar(
    searchState: SearchState,
    onEvent: (SearchUiEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }

    val textState = rememberTextFieldState(searchState.searchQuery)

    BasicTextField(
        state = textState,
        inputTransformation = {
            onEvent(SearchUiEvent.OnSearchQueryChange(textState.text.toString()))
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(primaryColor()),
        textStyle = TextStyle(
            color = onBackgroundColor(),
            fontSize = 17.sp
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .background(secondaryContainerColor())
            .focusRequester(focusRequester),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search_a_movie_or_tv_series),
                    tint = onBackgroundColor().copy(0.4f),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(28.dp)
                )

                Box(modifier = Modifier.weight(1f)) {
                    if (textState.text.isEmpty()) {
                        Text(
                            text = stringResource(
                                R.string.search_a_movie_or_tv_series
                            ),
                            color = onBackgroundColor().copy(0.4f),
                            fontSize = 17.sp
                        )
                    }

                    innerTextField()
                }

                if (textState.text.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(R.string.clear_search),
                        tint = onBackgroundColor().copy(0.6f),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(28.dp)
                            .clickable {
                                textState.edit {
                                    this.replace(
                                        0, this.length, ""
                                    )
                                }
                                onEvent(
                                    SearchUiEvent.OnSearchQueryChange("")
                                )
                            }
                    )
                }

            }
        }
    )
}


































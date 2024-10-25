package com.example.fullmovieapp_compose.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.auth.util.AuthResult
import com.example.fullmovieapp_compose.util.backgroundColor
import com.example.fullmovieapp_compose.util.onBackgroundColor
import com.example.fullmovieapp_compose.util.onPrimaryColor
import com.example.fullmovieapp_compose.util.primaryColor
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val profileState by profileViewModel.profileState.collectAsState()

    LaunchedEffect(true) {
        profileViewModel.logoutResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.SingedOut -> {
                    onLogout()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(backgroundColor())
            ) {
                Text(
                    text = stringResource(R.string.profile),
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .size(100.dp)
                    .background(primaryColor())
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profileState.name.take(1),
                    color = onPrimaryColor(),
                    fontSize = 80.sp
                )
            }

            Spacer(Modifier.height(64.dp))

            UserInfo(
                title = stringResource(R.string.name),
                value = profileState.name
            )

            Spacer(Modifier.height(32.dp))

            UserInfo(
                title = stringResource(R.string.email),
                value = profileState.email
            )

            Spacer(Modifier.height(64.dp))

            Button(
                onClick = {
                    profileViewModel.onEvent(ProfileUiEvent.Logout)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 70.dp)
            ) {
                Text(
                    text = stringResource(R.string.log_out)
                )
            }
        }
    }
}

@Composable
fun UserInfo(
    title: String,
    value: String
) {
    Text(
        text = title,
        color = onBackgroundColor().copy(0.7f),
        fontSize = 16.sp
    )

    Spacer(Modifier.height(6.dp))

    Text(
        text = value,
        color = onBackgroundColor(),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
}


















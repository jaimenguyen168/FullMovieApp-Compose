package com.example.fullmovieapp_compose.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.auth.util.AuthResult
import com.example.fullmovieapp_compose.util.onBackgroundColor
import com.example.fullmovieapp_compose.util.primaryColor
import com.example.fullmovieapp_compose.util.primaryContainerColor
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    onAuthorized: () -> Unit,
    onSignInClick: () -> Unit,
) {
    val registerViewModel = hiltViewModel<RegisterViewModel>()
    val registerState by registerViewModel.registerState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(true) {
        registerViewModel.authResultChannel.collectLatest { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    onAuthorized()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.an_unknown_error_occurred),
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(true) {
        registerViewModel.invalidCredentialsChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context,
                    context.getString(R.string.invalid_credentials),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = stringResource(R.string.app_icon),
            modifier = Modifier
                .size(18.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterHorizontally)
                .background(primaryContainerColor())
                .padding(30.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(R.string.create_your_account),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = onBackgroundColor()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.register),
            fontSize = 16.sp,
            color = onBackgroundColor()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registerState.name,
            onValueChange = {
                registerViewModel.onEvent(RegisterUiEvent.OnNameChanged(it))
            },
            label = {
                Text(
                    text = stringResource(R.string.enter_your_name),
                )
            },
            textStyle = TextStyle(fontSize = 15.sp),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registerState.email,
            onValueChange = {
                registerViewModel.onEvent(RegisterUiEvent.OnEmailChanged(it))
            },
            label = {
                Text(
                    text = stringResource(R.string.enter_your_email),
                )
            },
            textStyle = TextStyle(fontSize = 15.sp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        var passwordVisibility by rememberSaveable {
            mutableStateOf(false)
        }

        OutlinedTextField(
            value = registerState.password,
            onValueChange = {
                registerViewModel.onEvent(RegisterUiEvent.OnPasswordChanged(it))
            },
            label = {
                Text(
                    text = stringResource(R.string.enter_your_password),
                )
            },
            textStyle = TextStyle(fontSize = 15.sp),
            maxLines = 1,
            visualTransformation =
                if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                Icon(
                    imageVector =
                    if (passwordVisibility) Icons.Rounded.Visibility
                    else Icons.Rounded.VisibilityOff,
                    contentDescription =
                    if (passwordVisibility) stringResource(R.string.password_visible)
                    else stringResource(R.string.password_not_visible),
                    modifier = Modifier.clickable {
                        passwordVisibility = !passwordVisibility
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (registerState.isLoading) {
            Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .height(50.dp),
               contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Button(
                onClick = {
                    registerViewModel.onEvent(
                        RegisterUiEvent.Register
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 16.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                fontSize = 14.sp,
                color = onBackgroundColor()
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(R.string.log_in),
                fontSize = 14.sp,
                color = primaryColor(),
                modifier = Modifier.clickable {
                    onSignInClick()
                }
            )
        }
    }
}



















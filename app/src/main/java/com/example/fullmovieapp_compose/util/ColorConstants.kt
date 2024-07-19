package com.example.fullmovieapp_compose.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun primaryColor(): Color = MaterialTheme.colorScheme.primary

@Composable
fun onPrimaryColor(): Color = MaterialTheme.colorScheme.onPrimary

@Composable
fun backgroundColor(): Color = MaterialTheme.colorScheme.background

@Composable
fun onBackgroundColor(): Color = MaterialTheme.colorScheme.primary

@Composable
fun surfaceVariantColor(): Color = MaterialTheme.colorScheme.surfaceVariant

@Composable
fun secondaryContainerColor(): Color = MaterialTheme.colorScheme.secondaryContainer

@Composable
fun onSecondaryContainerColor(): Color = MaterialTheme.colorScheme.onSecondaryContainer

@Composable
fun primaryContainerColor(): Color = MaterialTheme.colorScheme.primaryContainer
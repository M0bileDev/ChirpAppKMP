package com.example.core.designsystem.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChirpSimpleSuccessLayout(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    primaryButton: @Composable () -> Unit,
    secondaryButton: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {

}
package com.example.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAdaptiveDialogSheetLayout(modifier: Modifier = Modifier) {
}

@Preview
@Composable
fun PreviewChirpAdaptiveDialogSheetLayout() {
    ChirpTheme { }
}

@Preview
@Composable
fun PreviewDarkChirpAdaptiveDialogSheetLayout() {
    ChirpTheme(darkTheme = true) { }
}
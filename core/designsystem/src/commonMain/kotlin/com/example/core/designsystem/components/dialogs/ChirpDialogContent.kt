package com.example.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpDialogContent(modifier: Modifier = Modifier) {
}

@Preview
@Composable
fun PreviewChirpDialogContent() {
    ChirpTheme { ChirpDialogContent() }
}

@Preview
@Composable
fun PreviewDarkChirpDialogContent() {
    ChirpTheme(darkTheme = true) { ChirpDialogContent() }
}
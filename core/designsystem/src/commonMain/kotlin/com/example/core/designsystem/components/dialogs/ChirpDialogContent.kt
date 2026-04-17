package com.example.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpDialogContent(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
}

@Preview
@Composable
fun PreviewChirpDialogContent() {
    ChirpTheme {
        ChirpDialogContent(
            onDismiss = {},
            content = {}
        )
    }
}

@Preview
@Composable
fun PreviewDarkChirpDialogContent() {
    ChirpTheme(darkTheme = true) {
        ChirpDialogContent(
            onDismiss = {},
            content = {}
        )
    }
}
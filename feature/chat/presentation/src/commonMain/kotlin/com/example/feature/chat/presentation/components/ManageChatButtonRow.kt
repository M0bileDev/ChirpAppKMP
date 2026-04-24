package com.example.feature.chat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ManageChatButtonRow(modifier: Modifier = Modifier) {
}

@Preview
@Composable
fun PreviewManageChatButtonRow() {
    ChirpTheme {
        ManageChatButtonRow()
    }
}

@Preview
@Composable
fun PreviewDarkManageChatButtonRow() {
    ChirpTheme(
        darkTheme = true
    ) {
        ManageChatButtonRow()
    }
}

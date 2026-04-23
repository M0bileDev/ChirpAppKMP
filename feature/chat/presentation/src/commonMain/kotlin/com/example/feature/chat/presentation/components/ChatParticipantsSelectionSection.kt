package com.example.feature.chat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatParticipantsSelectionSection(modifier: Modifier = Modifier) {
}

@Preview
@Composable
fun PreviewChatParticipantsSelectionSection() {
    ChirpTheme {
        ChatParticipantsSelectionSection()
    }
}

@Preview
@Composable
fun PreviewDarkChatParticipantsSelectionSection() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatParticipantsSelectionSection()
    }
}

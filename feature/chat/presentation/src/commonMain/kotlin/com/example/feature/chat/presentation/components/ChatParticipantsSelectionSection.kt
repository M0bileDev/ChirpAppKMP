package com.example.feature.chat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatParticipantsSelectionSection(
    selectedParticipants: List<ChatParticipantUi>,
    modifier: Modifier = Modifier,
    searchResult: ChatParticipantUi? = null
) {
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

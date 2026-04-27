package com.example.feature.chat.presentation.chat_list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatListItem(modifier: Modifier = Modifier) {}

@Preview
@Composable
fun PreviewChatListItem() {
    ChirpTheme {
        ChatListItem()
    }
}

@Preview
@Composable
fun PreviewDarkChatListItem() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatListItem()
    }
}
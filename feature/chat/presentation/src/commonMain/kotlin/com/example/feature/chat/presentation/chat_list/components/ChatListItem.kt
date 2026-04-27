package com.example.feature.chat.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import com.example.core.designsystem.theme.extended
import com.example.feature.chat.domain.Chat
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatListItem(
    chat: Chat,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val selectedColor =
        if (isSelected) MaterialTheme.colorScheme.surface
        else MaterialTheme.colorScheme.extended.surfaceLower

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(selectedColor)
            .fillMaxWidth()
    ) {

    }
}

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
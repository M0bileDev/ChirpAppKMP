package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature.chat.presentation.model.MessageUi

@Composable
fun MessageListItemUi(
    messageUi: MessageUi,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        when (messageUi) {
            is MessageUi.DateSeparator -> TODO()
            is MessageUi.OtherUserMessage -> TODO()
            is MessageUi.LocalUserMessage -> TODO()
        }
    }
}
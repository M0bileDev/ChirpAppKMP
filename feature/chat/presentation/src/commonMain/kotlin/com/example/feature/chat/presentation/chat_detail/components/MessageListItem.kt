package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
            is MessageUi.DateSeparator -> DateSeparatorItem(
                modifier = Modifier.fillMaxWidth(),
                date = messageUi.date.asString(),
            )

            is MessageUi.OtherUserMessage -> TODO()
            is MessageUi.LocalUserMessage -> LocalUserMessageItem(
                messageUi = messageUi,
                isMenuOpen = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DateSeparatorItem(
    date: String,
    modifier: Modifier = Modifier
) {
}

@Composable
fun LocalUserMessageItem(
    messageUi: MessageUi.LocalUserMessage,
    isMenuOpen: Boolean,
    modifier: Modifier = Modifier
) {
}
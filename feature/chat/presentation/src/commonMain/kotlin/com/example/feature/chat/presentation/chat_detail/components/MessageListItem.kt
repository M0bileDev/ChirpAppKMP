package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature.chat.presentation.model.MessageUi

@Composable
fun MessageListItem(
    messageUi: MessageUi,
    onMessageLongClick: (MessageUi.LocalUserMessage) -> Unit,
    onDismissMessageMenu: () -> Unit,
    onDeleteClick: (MessageUi.LocalUserMessage) -> Unit,
    onRetryClick: (MessageUi.LocalUserMessage) -> Unit,
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

            is MessageUi.OtherUserMessage -> OtherUserMessageItem(
                modifier = Modifier.fillMaxWidth(),
                messageUi = messageUi
            )

            is MessageUi.LocalUserMessage -> LocalUserMessageItem(
                messageUi = messageUi,
                modifier = Modifier.fillMaxWidth(),
                onMessageLongClick = { onMessageLongClick(messageUi) },
                onDismissMessageMenu = onDismissMessageMenu,
                onDeleteClick = { onDeleteClick(messageUi) },
                onRetryClick = { onRetryClick(messageUi) }
            )
        }
    }
}
package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.no_messages
import chirpappkmp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.example.feature.chat.presentation.components.EmptyListSection
import com.example.feature.chat.presentation.model.MessageUi
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageList(
    messages: List<MessageUi>,
    listState: LazyListState,
    onMessageLongClick: (MessageUi.LocalUserMessage) -> Unit,
    onMessageRetryClick: (MessageUi.LocalUserMessage) -> Unit,
    onDismissMessageMenu: () -> Unit,
    onDeleteMessageClick: (MessageUi.LocalUserMessage) -> Unit,
    modifier: Modifier = Modifier
) {
    if (messages.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            EmptyListSection(
                title = stringResource(Res.string.no_messages),
                description = stringResource(Res.string.no_messages_subtitle)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = PaddingValues(16.dp),
            reverseLayout = true
        ) {
            items(
                items = messages,
                key = { it.id }
            ) { message ->
                MessageListItem(
                    modifier = Modifier.fillMaxWidth().animateItem(),
                    messageUi = message,
                    onMessageLongClick = onMessageLongClick,
                    onDismissMessageMenu = onDismissMessageMenu,
                    onDeleteClick = onDeleteMessageClick,
                    onRetryClick = onMessageRetryClick
                )
            }
        }
    }
}
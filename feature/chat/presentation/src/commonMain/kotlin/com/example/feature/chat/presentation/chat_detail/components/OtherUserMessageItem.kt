package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.components.avatar.ChirpAvatarPhoto
import com.example.core.designsystem.components.chat.ChirpChatBubble
import com.example.core.designsystem.components.chat.TrianglePosition
import com.example.feature.chat.presentation.model.MessageUi

@Composable
fun OtherUserMessageItem(
    messageUi: MessageUi.OtherUserMessage,
    modifier: Modifier = Modifier
) = with(messageUi) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChirpAvatarPhoto(
            displayText = sender.initials,
            imageUrl = sender.imageUrl
        )
        ChirpChatBubble(
            messageContent = content,
            sender = sender.username,
            formattedDateTime = formattedSendAt.asString(),
            trianglePosition = TrianglePosition.LEFT
        )
    }
}
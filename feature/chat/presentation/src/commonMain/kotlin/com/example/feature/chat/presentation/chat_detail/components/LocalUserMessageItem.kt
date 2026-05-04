package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.you
import com.example.core.designsystem.components.chat.ChirpChatBubble
import com.example.core.designsystem.components.chat.TrianglePosition
import com.example.feature.chat.presentation.model.MessageUi
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocalUserMessageItem(
    messageUi: MessageUi.LocalUserMessage,
    onMessageLongClick: (MessageUi.LocalUserMessage) -> Unit,
    modifier: Modifier = Modifier
) = with(messageUi) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
    ) {
        Box {
            ChirpChatBubble(
                messageContent = content,
                sender = stringResource(Res.string.you),
                formattedDateTime = formattedSentAt.asString(),
                trianglePosition = TrianglePosition.RIGHT,
                messageStatus = {
                    MessageStatus(
                        status = deliveryStatus
                    )
                },
                onLongClick = {
                    onMessageLongClick(this@with)
                }
            )
        }
    }
}
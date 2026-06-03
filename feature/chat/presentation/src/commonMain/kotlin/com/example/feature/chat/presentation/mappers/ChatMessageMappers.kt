package com.example.feature.chat.presentation.mappers

import com.example.feature.chat.domain.model.MessageWithSender
import com.example.feature.chat.presentation.model.MessageUi
import com.example.feature.chat.presentation.util.DateUtils.formatMessageTime

fun MessageWithSender.toUi(
    localUserId: String,
): MessageUi {
    val isFromLocalUser = sender.userId == localUserId

    return if (isFromLocalUser) {
        MessageUi.LocalUserMessage(
            id = message.id,
            content = message.content,
            deliveryStatus = message.deliveryStatus,
            isMenuOpen = false,
            formattedSentAt = formatMessageTime(
                instant = message.createdAt
            ),
            canRetry = false
        )
    } else {
        MessageUi.OtherUserMessage(
            id = message.id,
            content = message.content,
            formattedSentAt = formatMessageTime(instant = message.createdAt),
            sender = sender.toUi()
        )
    }
}
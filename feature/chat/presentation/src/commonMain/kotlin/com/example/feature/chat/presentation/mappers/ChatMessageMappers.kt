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
            formattedSentAt = formatMessageTime(
                instant = message.createdAt
            ),
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

fun List<MessageWithSender>.toUiList(localUserId: String): List<MessageUi> {
    return sortedByDescending { messageWithSender ->
        messageWithSender.message.createdAt
    }.map { sortedMessageWithSender ->
        sortedMessageWithSender.toUi(localUserId = localUserId)
    }
}
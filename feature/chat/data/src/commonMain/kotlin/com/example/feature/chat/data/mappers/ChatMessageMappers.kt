package com.example.feature.chat.data.mappers

import com.example.feature.chat.data.dto.ChatMessageDto
import com.example.feature.chat.data.dto.websocket.OutgoingWebSocketDto
import com.example.feature.chat.database.entities.ChatMessageEntity
import com.example.feature.chat.database.view.LastMessageView
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = Instant.parse(createdAt),
        senderId = senderId,
        deliveryStatus = ChatMessageDeliveryStatus.SENT
    )
}

fun LastMessageView.toDomain(): ChatMessage {
    return ChatMessage(
        id = messageId,
        chatId = chatId,
        content = content,
        createdAt = Instant.fromEpochMilliseconds(timestamp),
        senderId = senderId,
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(this.deliveryStatus)
    )
}

fun ChatMessage.toEntity(): ChatMessageEntity {
    return ChatMessageEntity(
        messageId = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        timestamp = createdAt.toEpochMilliseconds(),
        deliveryStatus = deliveryStatus.name
    )
}

fun ChatMessage.toLastMessageView(): LastMessageView {
    return LastMessageView(
        messageId = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        timestamp = createdAt.toEpochMilliseconds(),
        deliveryStatus = deliveryStatus.name
    )
}

fun ChatMessageEntity.toDomain(): ChatMessage {
    return ChatMessage(
        id = chatId,
        chatId = chatId,
        content = content,
        createdAt = Instant.fromEpochMilliseconds(timestamp),
        senderId = senderId,
        deliveryStatus = ChatMessageDeliveryStatus.SENT
    )
}

fun ChatMessage.toNewMessage(): OutgoingWebSocketDto.NewMessage {
    return OutgoingWebSocketDto.NewMessage(
        chatId = chatId,
        messageId = id,
        content = content
    )
}
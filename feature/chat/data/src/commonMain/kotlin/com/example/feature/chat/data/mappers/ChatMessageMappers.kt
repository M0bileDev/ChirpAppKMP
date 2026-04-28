package com.example.feature.chat.data.mappers

import com.example.feature.chat.data.dto.ChatMessageDto
import com.example.feature.chat.domain.model.ChatMessage
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = Instant.parse(createdAt),
        senderId = senderId
    )
}
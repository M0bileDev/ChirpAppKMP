package com.example.feature.chat.data.mappers

import com.example.feature.chat.data.dto.ChatDto
import com.example.feature.chat.domain.model.Chat
import kotlin.time.Instant

fun ChatDto.toDomain() : Chat{
    return Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}
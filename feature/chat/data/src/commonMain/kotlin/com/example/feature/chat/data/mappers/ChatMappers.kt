package com.example.feature.chat.data.mappers

import com.example.feature.chat.data.dto.ChatDto
import com.example.feature.chat.database.entities.ChatEntity
import com.example.feature.chat.database.entities.ChatInfoEntity
import com.example.feature.chat.database.entities.ChatWithParticipants
import com.example.feature.chat.domain.model.Chat
import com.example.feature.chat.domain.model.ChatInfo
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ChatParticipant
import kotlin.time.Instant

fun ChatDto.toDomain(): Chat {
    return Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}

fun ChatWithParticipants.toDomain(): Chat {
    return Chat(
        id = chat.chatId,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}

fun Chat.toEntity(): ChatEntity {
    return ChatEntity(
        chatId = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds()
    )
}

fun ChatEntity.toDomain(
    participants: List<ChatParticipant>,
    lastMessage: ChatMessage? = null
): Chat {
    return Chat(
        id = chatId,
        participants = participants,
        lastActivityAt = Instant.fromEpochMilliseconds(lastActivityAt),
        lastMessage = lastMessage
    )
}


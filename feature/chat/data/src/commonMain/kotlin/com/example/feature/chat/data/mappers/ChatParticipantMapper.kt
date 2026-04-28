package com.example.feature.chat.data.mappers

import com.example.feature.chat.data.dto.ChatParticipantDto
import com.example.feature.chat.domain.model.ChatParticipant

fun ChatParticipantDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}
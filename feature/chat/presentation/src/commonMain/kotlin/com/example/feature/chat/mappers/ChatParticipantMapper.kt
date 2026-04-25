package com.example.feature.chat.mappers

import com.example.feature.chat.domain.ChatParticipant
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}
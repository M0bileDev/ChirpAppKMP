package com.example.feature.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val otherUserIds: List<String>
)
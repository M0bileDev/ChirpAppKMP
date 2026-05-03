package com.example.feature.chat.presentation.model

import com.example.core.presentation.util.UiText
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus

sealed interface MessageUi {
    data class LocalUserMessage(
        val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val canRetry: Boolean,
        val formattedSentAt: UiText
    )
}
package com.example.feature.chat.presentation.model

import com.example.core.presentation.util.UiText
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi

sealed interface MessageUi {
    data class LocalUserMessage(
        val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val canRetry: Boolean,
        val formattedSentAt: UiText,
        val isMenuOpen: Boolean
    ) : MessageUi

    data class OtherUserMessage(
        val id: String,
        val content: String,
        val formattedSendAt: UiText,
        val sender: ChatParticipantUi
    ) : MessageUi

    data class DateSeparator(
        val id: String,
        val date: UiText
    ) : MessageUi
}
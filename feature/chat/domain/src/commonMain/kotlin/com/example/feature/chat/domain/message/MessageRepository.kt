package com.example.feature.chat.domain.message

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>
}
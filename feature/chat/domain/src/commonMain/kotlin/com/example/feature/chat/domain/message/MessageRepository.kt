package com.example.feature.chat.domain.message

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.example.feature.chat.domain.model.MessageWithSender
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>

    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError>

    fun getMessagesForChat(
        chatId: String
    ): Flow<List<MessageWithSender>>
}
package com.example.feature.chat.data.message

import com.example.core.data.database.safeDatabaseUpdate
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.domain.message.MessageRepository
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val chirpChatDatabase: ChirpChatDatabase
) : MessageRepository {
    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> = with(chirpChatDatabase) {
        return@with safeDatabaseUpdate {
            chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                status = status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }
    }

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError> {
        TODO("Not yet implemented")
    }

    override fun getMessagesForChat(chatId: String): Flow<List<ChatMessage>> {
        TODO("Not yet implemented")
    }
}
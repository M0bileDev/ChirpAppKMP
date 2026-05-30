package com.example.feature.chat.data.message

import com.example.core.data.database.safeDatabaseUpdate
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.domain.message.MessageRepository
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
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
}
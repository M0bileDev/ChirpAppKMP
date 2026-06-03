package com.example.feature.chat.data.message

import com.example.core.data.database.safeDatabaseUpdate
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.onSuccess
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.data.mappers.toEntity
import com.example.feature.chat.data.message.ChatMessageConstants.PAGE_SIZE
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.domain.message.ChatMessageService
import com.example.feature.chat.domain.message.MessageRepository
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val chirpChatDatabase: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService
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
        return chatMessageService
            .fetchMessages(chatId, before)
            .onSuccess { messages ->
                safeDatabaseUpdate {
                    val entities = messages.map { it.toEntity() }
                    val mostRecentPage = before == null

                    chirpChatDatabase.chatMessageDao.upsertMessagesAndSyncIfNecessary(
                        chatId = chatId,
                        serverMessages = entities,
                        pageSize = PAGE_SIZE,
                        shouldSync = mostRecentPage
                    )
                }
            }
    }

    override fun getMessagesForChat(chatId: String): Flow<List<ChatMessage>> {
        return chirpChatDatabase
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { messages ->
                messages.map { message -> message.toDomain() }
            }
    }
}
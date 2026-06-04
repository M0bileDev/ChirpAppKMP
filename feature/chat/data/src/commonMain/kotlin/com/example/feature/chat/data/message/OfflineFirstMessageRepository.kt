package com.example.feature.chat.data.message

import com.example.core.data.database.safeDatabaseUpdate
import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.onSuccess
import com.example.feature.chat.data.dto.websocket.OutgoingWebSocketDto
import com.example.feature.chat.data.dto.websocket.WebSocketMessageDto
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.data.mappers.toEntity
import com.example.feature.chat.data.mappers.toWebsocketDto
import com.example.feature.chat.data.message.ChatMessageConstants.PAGE_SIZE
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.domain.message.ChatMessageService
import com.example.feature.chat.domain.message.MessageRepository
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.example.feature.chat.domain.model.MessageWithSender
import com.example.feature.chat.domain.model.OutgoingNewMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val chirpChatDatabase: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService,
    private val sessionStorage: SessionStorage,
    private val json: Json
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

    override fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>> {
        return chirpChatDatabase
            .chatMessageDao
            .getMessagesWithSenderByChatId(chatId)
            .map { messages ->
                messages.map { message -> message.toDomain() }
            }
    }

    override suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError> {
        return safeDatabaseUpdate {
            val dto = message.toWebsocketDto()
            val localUser =
                sessionStorage.observeAuthInfo().first()?.user ?: return Result.Failure(
                    DataError.Local.NOT_FOUND
                )
            val entity = dto.toEntity(
                senderId = localUser.id,
                deliveryStatus = ChatMessageDeliveryStatus.SENDING
            )

            chirpChatDatabase.chatMessageDao.upsertMessage(
                message = entity
            )

            // TODO: send message through websocket
        }
    }

    private fun OutgoingWebSocketDto.NewMessage.toJsonPayload(): String {
        val webSocketMessage = WebSocketMessageDto(
            type = type.name,
            payload = json.encodeToString(this)
        )

        return json.encodeToString(webSocketMessage)
    }
}
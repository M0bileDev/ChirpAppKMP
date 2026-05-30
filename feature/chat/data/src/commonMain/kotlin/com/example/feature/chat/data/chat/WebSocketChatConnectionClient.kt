package com.example.feature.chat.data.chat

import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.onFailure
import com.example.feature.chat.data.dto.websocket.WebSocketMessageDto
import com.example.feature.chat.data.mappers.toNewMessage
import com.example.feature.chat.data.network.KtorWebSocketConnector
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.domain.chat.ChatConnectionClient
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.domain.error.ConnectionError
import com.example.feature.chat.domain.message.MessageRepository
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClient(
    private val ktorWebSocketConnector: KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val chirpChatDatabase: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val messageRepository: MessageRepository
) : ChatConnectionClient {
    override val chatMessages: Flow<ChatMessage>
        get() = TODO("Not yet implemented")
    override val connectionState = ktorWebSocketConnector.connectionState

    override suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError> {
        val outgoingDto = message.toNewMessage()
        val webSocketMessage = WebSocketMessageDto(
            type = outgoingDto.type.name,
            payload = json.encodeToString(outgoingDto)
        )

        val rawJsonPayload = json.encodeToString(webSocketMessage)
        return ktorWebSocketConnector
            .sendMessage(rawJsonPayload)
            .onFailure { _ ->
                messageRepository.updateMessageDeliveryStatus(
                    messageId = message.id,
                    status = ChatMessageDeliveryStatus.FAILED
                )
            }
    }
}
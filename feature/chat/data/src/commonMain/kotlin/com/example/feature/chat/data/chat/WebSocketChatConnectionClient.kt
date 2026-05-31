package com.example.feature.chat.data.chat

import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.onFailure
import com.example.feature.chat.data.dto.websocket.IncomingWebSocketDto
import com.example.feature.chat.data.dto.websocket.IncomingWebSocketType
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
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClient(
    private val ktorWebSocketConnector: KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val chirpChatDatabase: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val messageRepository: MessageRepository
) : ChatConnectionClient {
    override val chatMessages =
        ktorWebSocketConnector
            .messages
            .mapNotNull { webSocketMessageDto -> webSocketMessageDto.parseIncomingMessage() }
            .onEach { incomingWebSocketDto -> incomingWebSocketDto.handleIncomingMessage() }

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

    private fun WebSocketMessageDto.parseIncomingMessage(): IncomingWebSocketDto? {
        return when (type) {
            IncomingWebSocketType.NEW_MESSAGE.name -> {
                json.decodeFromString<IncomingWebSocketDto.NewMessageDto>(payload)
            }

            IncomingWebSocketType.MESSAGE_DELETED.name -> {
                json.decodeFromString<IncomingWebSocketDto.MessageDeletedDto>(payload)
            }

            IncomingWebSocketType.PROFILE_PICTURE_UPDATED.name -> {
                json.decodeFromString<IncomingWebSocketDto.ProfilePictureUpdated>(payload)
            }

            IncomingWebSocketType.CHAT_PARTICIPANTS_CHANGED.name -> {
                json.decodeFromString<IncomingWebSocketDto.ChatParticipantsChangedDto>(payload)
            }

            else -> null
        }
    }

    private suspend fun IncomingWebSocketDto.handleIncomingMessage() {
        when (this) {
            is IncomingWebSocketDto.ChatParticipantsChangedDto -> refreshChat()
            is IncomingWebSocketDto.MessageDeletedDto -> deleteMessage()
            is IncomingWebSocketDto.NewMessageDto -> handleNewMessage()
            is IncomingWebSocketDto.ProfilePictureUpdated -> updateProfilePicture()
        }
    }

    private suspend fun IncomingWebSocketDto.ChatParticipantsChangedDto.refreshChat() {
        // TODO: implement
    }

    private suspend fun IncomingWebSocketDto.MessageDeletedDto.deleteMessage() {
        // TODO: implement
    }

    private suspend fun IncomingWebSocketDto.NewMessageDto.handleNewMessage() {
        // TODO: implement
    }

    private suspend fun IncomingWebSocketDto.ProfilePictureUpdated.updateProfilePicture() {
        // TODO: implement
    }
}
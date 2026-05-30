package com.example.feature.chat.data.chat

import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.util.EmptyResult
import com.example.feature.chat.data.network.KtorWebSocketConnector
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.domain.chat.ChatConnectionClient
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.domain.error.ConnectionError
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ConnectionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClient(
    private val ktorWebSocketConnector: KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val chirpChatDatabase: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json
) : ChatConnectionClient {
    override val chatMessages: Flow<ChatMessage>
        get() = TODO("Not yet implemented")
    override val connectionState = ktorWebSocketConnector.connectionState

    override suspend fun sendChatMessage(message: String): EmptyResult<ConnectionError> {
        TODO("Not yet implemented")
    }
}
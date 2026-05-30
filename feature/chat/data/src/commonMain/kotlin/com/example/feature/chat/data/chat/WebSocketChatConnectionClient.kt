package com.example.feature.chat.data.chat

import com.example.core.domain.util.EmptyResult
import com.example.feature.chat.domain.chat.ChatConnectionClient
import com.example.feature.chat.domain.error.ConnectionError
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ConnectionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class WebSocketChatConnectionClient : ChatConnectionClient {
    override val chatMessages: Flow<ChatMessage>
        get() = TODO("Not yet implemented")
    override val connectionState: StateFlow<ConnectionState>
        get() = TODO("Not yet implemented")

    override suspend fun sendChatMessage(message: String): EmptyResult<ConnectionError> {
        TODO("Not yet implemented")
    }
}
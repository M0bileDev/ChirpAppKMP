package com.example.feature.chat.domain.chat

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.feature.chat.domain.error.ConnectionError
import com.example.feature.chat.domain.model.ChatMessage
import com.example.feature.chat.domain.model.ConnectionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatConnectionClient {
    val chatMessages: Flow<ChatMessage>
    val connectionState: StateFlow<ConnectionState>
    suspend fun sendChatMessage(message: ChatMessage): EmptyResult<DataError.Connection>
}
package com.example.feature.chat.data.message

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.feature.chat.domain.message.ChatMessageService
import com.example.feature.chat.domain.model.ChatMessage
import io.ktor.client.HttpClient

class KtorChatMessageService(
    private val httpClient: HttpClient,
) : ChatMessageService

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError.Remote> {
        TODO("Not yet implemented")
    }

}
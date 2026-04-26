package com.example.feature.chat.data.chat

import com.example.core.data.network.post
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import com.example.feature.chat.data.dto.ChatDto
import com.example.feature.chat.data.dto.CreateChatRequest
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.domain.Chat
import com.example.feature.chat.domain.chat.ChatService
import io.ktor.client.HttpClient

class KtorChatService(
    val httpClient: HttpClient
) : ChatService {
    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return httpClient.post<CreateChatRequest, ChatDto>(
            route = "/chat",
            body = CreateChatRequest(
                otherUserIds = otherUserIds
            )
        ).map {
            it.toDomain()
        }
    }
}
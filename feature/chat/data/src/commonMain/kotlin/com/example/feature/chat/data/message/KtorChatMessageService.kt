package com.example.feature.chat.data.message

import com.example.core.data.network.get
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import com.example.feature.chat.data.dto.ChatMessageDto
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.domain.message.ChatMessageService
import com.example.feature.chat.domain.model.ChatMessage
import io.ktor.client.HttpClient

class KtorChatMessageService(
    private val httpClient: HttpClient,
) : ChatMessageService {

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError.Remote> {
        return httpClient.get<List<ChatMessageDto>>(
            route = "/chat/$chatId/messages",
            queryParams = buildMap {
                if (before != null) {
                    this["before"] = before
                }
            }
        ).map { chatMessagesDto ->
            chatMessagesDto.map { chatMessageDto -> chatMessageDto.toDomain() }
        }
    }

}
package com.example.feature.chat.data.participant

import com.example.core.data.network.get
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import com.example.feature.chat.data.dto.ChatParticipantDto
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.domain.participant.ChatParticipantService
import com.example.feature.chat.domain.model.ChatParticipant
import io.ktor.client.HttpClient

class KtorChatParticipantService(
    val httpClient: HttpClient
) : ChatParticipantService {

    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants",
            queryParams = mapOf(
                "query" to query
            )
        ).map { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getLocalParticipant(): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants"
        ).map { dto ->
            dto.toDomain()
        }
    }
}
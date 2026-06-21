package com.example.feature.chat.data.participant

import com.example.core.data.network.get
import com.example.core.data.network.post
import com.example.core.data.network.put
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import com.example.feature.chat.data.dto.ChatParticipantDto
import com.example.feature.chat.data.dto.request.ConfirmProfilePictureRequest
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.domain.model.ChatParticipant
import com.example.feature.chat.domain.model.ProfilePictureUploadUrls
import com.example.feature.chat.domain.participant.ChatParticipantService
import io.ktor.client.HttpClient
import io.ktor.client.request.header

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

    override suspend fun getProfilePictureUploadUrl(mimeType: String): Result<ProfilePictureUploadUrls, DataError.Remote> {
        return httpClient.post(
            route = "/participants/profile-picture-upload",
            queryParams = mapOf(
                "mimeType" to mimeType
            ),
            body = Unit
        )
    }

    override suspend fun uploadProfilePicture(
        uploadUrl: String,
        imageBytes: ByteArray,
        headers: Map<String, String>
    ): EmptyResult<DataError.Remote> {
        return httpClient.put(
            route = uploadUrl,
            body = imageBytes,
        ) {
            headers.forEach { (headerName, headerValue) ->
                header(headerName, headerValue)
            }
        }
    }

    override suspend fun confirmProfilePictureUpload(publicUrl: String): EmptyResult<DataError.Remote> {
        return httpClient.post<ConfirmProfilePictureRequest, Unit>(
            route = "/participants/confirm-profile-picture",
            body = ConfirmProfilePictureRequest(publicUrl = publicUrl)
        )
    }
}
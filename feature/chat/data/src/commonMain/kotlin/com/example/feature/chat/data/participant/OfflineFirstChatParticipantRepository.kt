package com.example.feature.chat.data.participant

import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.onSuccess
import com.example.feature.chat.domain.model.ChatParticipant
import com.example.feature.chat.domain.participant.ChatParticipantRepository
import com.example.feature.chat.domain.participant.ChatParticipantService
import kotlinx.coroutines.flow.first

class OfflineFirstChatParticipantRepository(
    private val sessionStorage: SessionStorage,
    private val chatParticipantService: ChatParticipantService
) : ChatParticipantRepository {

    override suspend fun fetchLocalParticipant(): Result<ChatParticipant, DataError> {
        return chatParticipantService
            .getLocalParticipant()
            .onSuccess { participant ->
                val currentAuthInfo = sessionStorage.observeAuthInfo().first()
                with(participant) {
                    sessionStorage.set(
                        currentAuthInfo?.copy(
                            user = currentAuthInfo.user.copy(
                                id = userId,
                                username = username,
                                profilePictureUrl = profilePictureUrl
                            )
                        )
                    )
                }
            }
    }
}
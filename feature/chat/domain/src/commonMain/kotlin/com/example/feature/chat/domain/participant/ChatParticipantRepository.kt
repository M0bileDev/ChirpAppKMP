package com.example.feature.chat.domain.participant

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.feature.chat.domain.model.ChatParticipant

interface ChatParticipantRepository {
    suspend fun fetchLocalParticipant(): Result<ChatParticipant, DataError>
}
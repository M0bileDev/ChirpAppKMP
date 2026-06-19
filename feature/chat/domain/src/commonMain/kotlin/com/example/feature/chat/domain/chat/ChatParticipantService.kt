package com.example.feature.chat.domain.chat

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.feature.chat.domain.model.ChatParticipant

interface ChatParticipantService {

    suspend fun searchParticipant(
        query: String
    ): Result<ChatParticipant, DataError.Remote>

    suspend fun getLocalParticipant(): Result<ChatParticipant, DataError.Remote>
}
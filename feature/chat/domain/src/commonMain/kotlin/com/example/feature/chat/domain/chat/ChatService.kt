package com.example.feature.chat.domain.chat

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.feature.chat.domain.model.Chat

interface ChatService {
    suspend fun createChat(
        otherUserIds: List<String>
    ): Result<Chat, DataError.Remote>
}
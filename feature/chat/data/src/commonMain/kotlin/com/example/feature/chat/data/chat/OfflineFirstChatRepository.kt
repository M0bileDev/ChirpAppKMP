package com.example.feature.chat.data.chat

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.domain.chat.ChatService
import com.example.feature.chat.domain.model.Chat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val chirpChatDatabase: ChirpChatDatabase
) : ChatRepository {

    override fun getChats(): Flow<List<Chat>> {
        return chirpChatDatabase
            .chatDao
            .getChatsWithActiveParticipants()
            .map { chatWithParticipants ->
                chatWithParticipants.map { it.toDomain() }
            }

    }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> {
        TODO("Not yet implemented")
    }
}
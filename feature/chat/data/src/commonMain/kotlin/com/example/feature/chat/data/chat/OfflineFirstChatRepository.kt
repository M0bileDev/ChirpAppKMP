package com.example.feature.chat.data.chat

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.onSuccess
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.data.mappers.toEntity
import com.example.feature.chat.data.mappers.toLastMessageView
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.database.entities.ChatWithParticipants
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
        return chatService
            .getChats()
            .onSuccess { chats ->
                saveChats(chats)
            }
    }

    private suspend fun saveChats(chats: List<Chat>) {
        val chatsWithParticipants = chats.map { chat ->
            ChatWithParticipants(
                chat = chat.toEntity(),
                participants = chat.participants.map { participant -> participant.toEntity() },
                lastMessage = chat.lastMessage?.toLastMessageView()
            )
        }

        with(chirpChatDatabase) {
            chatDao
                .upsertChatsWithParticipantsAndCrossRefs(
                    chatsWithParticipants = chatsWithParticipants,
                    participantDao = chatParticipantDao,
                    crossRefDao = chatParticipantsCrossRefDao,
                    messageDao = chatMessageDao
                )
        }
    }
}
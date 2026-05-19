package com.example.feature.chat.data.chat

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.asEmptyResult
import com.example.core.domain.util.onSuccess
import com.example.feature.chat.data.mappers.toDomain
import com.example.feature.chat.data.mappers.toEntity
import com.example.feature.chat.data.mappers.toLastMessageView
import com.example.feature.chat.database.ChirpChatDatabase
import com.example.feature.chat.database.entities.ChatWithParticipants
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.domain.chat.ChatService
import com.example.feature.chat.domain.model.Chat
import com.example.feature.chat.domain.model.ChatInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
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

    override suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote> {
        return chatService
            .getChatById(chatId)
            .onSuccess { chat ->
                with(chirpChatDatabase) {
                    chatDao.upsertChatWithParticipantsAndCrossRefs(
                        chat = chat.toEntity(),
                        participants = chat.participants.map { it.toEntity() },
                        participantDao = chatParticipantDao,
                        crossRefDao = chatParticipantsCrossRefDao
                    )
                }
            }.asEmptyResult()
    }

    override fun getChatInfoById(chatId: String): Flow<ChatInfo> = with(chirpChatDatabase) {
        return@with chatDao
            .getActiveChatInfoById(chatId)
            .filterNotNull()
            .map { it.toDomain() }
    }
}
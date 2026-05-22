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
            .getChatsWithParticipants()
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

    private suspend fun saveChats(chats: List<Chat>) = with(chirpChatDatabase) {
        val chatsWithParticipants = chats.map { chat ->
            ChatWithParticipants(
                chat = chat.toEntity(),
                participants = chat.participants.map { participant -> participant.toEntity() },
                lastMessage = chat.lastMessage?.toLastMessageView()
            )
        }
        chatDao
            .upsertChatsWithParticipantsAndCrossRefs(
                chatsWithParticipants = chatsWithParticipants,
                participantDao = chatParticipantDao,
                crossRefDao = chatParticipantsCrossRefDao,
                messageDao = chatMessageDao
            )
    }

    override suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote> =
        with(chirpChatDatabase) {
            return@with chatService
                .getChatById(chatId)
                .onSuccess { chat ->
                    chatDao.upsertChatWithParticipantsAndCrossRefs(
                        chat = chat.toEntity(),
                        participants = chat.participants.map { it.toEntity() },
                        participantDao = chatParticipantDao,
                        crossRefDao = chatParticipantsCrossRefDao
                    )
                }.asEmptyResult()
        }

    override fun getChatInfoById(chatId: String): Flow<ChatInfo> = with(chirpChatDatabase) {
        return@with chatDao
            .getChatInfoById(chatId)
            .filterNotNull()
            .map { it.toDomain() }
    }

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> =
        with(chirpChatDatabase) {
            return chatService
                .createChat(otherUserIds)
                .onSuccess { chat ->
                    chatDao.upsertChatWithParticipantsAndCrossRefs(
                        chat = chat.toEntity(),
                        participants = chat.participants.map { it.toEntity() },
                        participantDao = chatParticipantDao,
                        crossRefDao = chatParticipantsCrossRefDao
                    )
                }
        }

    override suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote> {
        return chatService
            .leaveChat(chatId)
            .onSuccess {
                chirpChatDatabase.chatDao.deleteChatById(chatId)
            }
    }
}
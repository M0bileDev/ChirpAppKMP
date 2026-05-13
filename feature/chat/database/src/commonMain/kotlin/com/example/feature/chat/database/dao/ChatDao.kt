package com.example.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.feature.chat.database.entities.ChatEntity
import com.example.feature.chat.database.entities.ChatWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsertChat(chat: ChatEntity)

    @Upsert
    suspend fun upsertChats(chats: List<ChatEntity>)

    @Query("DELETE FROM chatentity WHERE chatId = :chatId")
    suspend fun deleteChatById(chatId: String)

    @Query("SELECT * FROM chatentity ORDER BY lastActivityAt DESC")
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>

    @Query("SELECT * FROM chatentity WHERE chatId = :chatId")
    fun getChatById(chatId: String): ChatWithParticipants?

    @Query("DELETE FROM chatentity")
    suspend fun deleteAllChats()
}
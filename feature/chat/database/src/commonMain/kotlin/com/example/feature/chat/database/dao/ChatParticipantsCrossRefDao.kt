package com.example.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.feature.chat.database.entities.ChatParticipantCrossRef

@Dao
interface ChatParticipantsCrossRefDao {

    @Upsert
    suspend fun upsertCrossRefs(crossRefs: List<ChatParticipantCrossRef>)

    @Query("SELECT userId FROM chatparticipantcrossref WHERE chatId = :chatId AND isActive = 1")
    suspend fun getActiveParticipantIdsByChat(chatId: String): List<String>
}
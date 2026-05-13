package com.example.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.feature.chat.database.entities.ChatParticipantEntity

@Dao
interface ChatParticipantDao {

    @Upsert
    suspend fun upsertParticipant(participant: ChatParticipantEntity)

    @Upsert
    suspend fun upsertParticipants(participants: List<ChatParticipantEntity>)
}
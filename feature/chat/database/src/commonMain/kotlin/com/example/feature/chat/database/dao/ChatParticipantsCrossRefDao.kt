package com.example.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.feature.chat.database.entities.ChatParticipantCrossRef

@Dao
interface ChatParticipantsCrossRefDao {

    @Upsert
    suspend fun upsertCrossRefs(crossRefs: List<ChatParticipantCrossRef>)
}
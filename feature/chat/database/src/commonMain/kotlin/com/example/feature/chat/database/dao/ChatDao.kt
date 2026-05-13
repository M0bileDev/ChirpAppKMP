package com.example.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.feature.chat.database.entities.ChatEntity

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsertChat(chat: ChatEntity)
}
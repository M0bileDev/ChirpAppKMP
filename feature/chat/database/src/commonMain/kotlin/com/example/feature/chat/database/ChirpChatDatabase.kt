package com.example.feature.chat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.feature.chat.database.entities.ChatEntity
import com.example.feature.chat.database.entities.ChatMessageEntity
import com.example.feature.chat.database.entities.ChatParticipantCrossRef
import com.example.feature.chat.database.entities.ChatParticipantEntity

@Database(
    entities = [
        ChatEntity::class,
        ChatParticipantEntity::class,
        ChatMessageEntity::class,
        ChatParticipantCrossRef::class
    ],
    version = 1
)
abstract class ChirpChatDatabase : RoomDatabase() {

}
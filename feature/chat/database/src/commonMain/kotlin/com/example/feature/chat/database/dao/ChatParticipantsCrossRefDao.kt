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

    @Query(
        """
        UPDATE chatparticipantcrossref
        SET isActive = 0
        WHERE chatId = :chatId AND userId IN (:userIds)
    """
    )
    suspend fun markParticipantsAsInactive(chatId: String, userIds: List<String>)

    @Query(
        """
        UPDATE chatparticipantcrossref
        SET isActive = 1
        WHERE chatId = :chatId AND userId IN (:userIds)
    """
    )
    suspend fun markParticipantsAsActive(chatId: String, userIds: List<String>)

    @Query("SELECT userId FROM chatparticipantcrossref")
    suspend fun getParticipantIdsByChat(chatId: String): List<String>
}
package com.example.feature.chat.domain.chat


import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.feature.chat.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
}
package com.example.feature.chat.database.view

data class LastMessageView(
    val messageId: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long
)
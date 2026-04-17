package com.example.feature.chat.domain

data class ChatInfo(
    val chat: Chat,
    val listOfMessageWithSender: List<MessageWithSender>
)
package com.example.feature.chat.domain

data class MessageWithSender(
    val message: ChatMessage,
    val sender: ChatParticipant,
    val status: ChatMessageDeliveryStatus?
)
package com.example.feature.chat.data.dto.websocket

import kotlinx.serialization.Serializable

@Serializable
sealed class IncomingWebSocketDto(
    val type: IncomingWebSocketType
) {

    @Serializable
    data class NewMessageDto(
        val id: String,
        val chatId: String,
        val content: String,
        val senderId: String,
        val createdAt: String
    ) : IncomingWebSocketDto(type = IncomingWebSocketType.NEW_MESSAGE)

    @Serializable
    data class MessageDeletedDto(
        val messageId: String,
        val chatId: String
    ) : IncomingWebSocketDto(type = IncomingWebSocketType.MESSAGE_DELETED)

    @Serializable
    data class ProfilePictureUpdated(
        val userId: String,
        val newUrl: String?
    ) : IncomingWebSocketDto(type = IncomingWebSocketType.PROFILE_PICTURE_UPDATED)

    @Serializable
    data class ChatParticipantsChangedDto(
        val chatId: String
    ) : IncomingWebSocketDto(type = IncomingWebSocketType.CHAT_PARTICIPANTS_CHANGED)
}
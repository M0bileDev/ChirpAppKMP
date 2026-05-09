package com.example.feature.chat.presentation.chat_detail

import androidx.compose.foundation.text.input.TextFieldState
import com.example.core.presentation.util.UiText
import com.example.feature.chat.presentation.model.ChatUi
import com.example.feature.chat.presentation.model.MessageUi

data class ChatDetailState(
    val chatUi: ChatUi? = null,
    val messages: List<MessageUi> = emptyList(),
    val messageTextFieldState: TextFieldState = TextFieldState(),
    val canSendMessage: Boolean = false,
    val isChatLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val error: UiText? = null,
    val paginationError: UiText? = null,
    val endReached: Boolean = false
)
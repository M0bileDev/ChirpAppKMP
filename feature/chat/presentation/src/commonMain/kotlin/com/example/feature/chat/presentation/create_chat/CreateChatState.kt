package com.example.feature.chat.presentation.create_chat

import androidx.compose.foundation.text.input.TextFieldState
import com.example.core.presentation.util.UiText
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi

data class CreateChatState(
    val queryTextState: TextFieldState = TextFieldState(),
    val selectedChatParticipants: List<ChatParticipantUi> = emptyList(),
    val isAddingParticipant: Boolean = false,
    val isLoadingParticipant: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipantUi? = null,
    val searchError: UiText? = null
)
package com.example.feature.chat.presentation.create_chat

import androidx.compose.foundation.text.input.TextFieldState
import com.example.core.designsystem.components.avatar.AvatarUi
import com.example.core.presentation.util.UiText

typealias ChatParticipantUi = AvatarUi

data class CreateChatState(
    val queryTextState: TextFieldState = TextFieldState(),
    val selectedChatParticipants: List<ChatParticipantUi> = emptyList(),
    val isAddingParticipants: Boolean = false,
    val isLoadingParticipants: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipantUi? = null,
    val searchError: UiText? = null
)
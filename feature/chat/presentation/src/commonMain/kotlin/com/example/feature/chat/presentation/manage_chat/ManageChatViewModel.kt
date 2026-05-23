package com.example.feature.chat.presentation.manage_chat

import androidx.lifecycle.ViewModel
import com.example.feature.chat.domain.chat.ChatRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ManageChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _eventChannel = Channel<ManageChatEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ManageChatState())
    val state = _state.asStateFlow()

    fun onAction(manageChatAction: ManageChatAction) {
        when (manageChatAction) {
            ManageChatAction.ChatParticipantsAction.OnAddParticipantClick -> TODO()
            is ManageChatAction.ChatParticipantsAction.OnSelectChat -> TODO()
            else -> Unit
        }
    }
}
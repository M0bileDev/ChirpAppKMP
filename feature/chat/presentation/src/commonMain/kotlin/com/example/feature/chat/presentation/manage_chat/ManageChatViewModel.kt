package com.example.feature.chat.presentation.manage_chat

import androidx.lifecycle.ViewModel
import com.example.feature.chat.domain.chat.ChatRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class ManageChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _chatId = MutableStateFlow<String?>(null)
    private val _eventChannel = Channel<ManageChatEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ManageChatState())
    val state = _state.asStateFlow()

    fun onAction(manageChatAction: ManageChatAction) {
        when (manageChatAction) {
            ManageChatAction.OnAddClick -> TODO()
            is ManageChatAction.ChatParticipantsAction.OnSelectChat -> onSelectChat(manageChatAction.chatId)
            else -> Unit
        }
    }

    private fun onSelectChat(chatId: String) {
        _chatId.update { chatId }
    }
}
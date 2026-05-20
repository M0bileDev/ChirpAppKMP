package com.example.feature.chat.presentation.chat_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.auth.SessionStorage
import com.example.feature.chat.domain.chat.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatDetailViewModel(
    private val chatRepository: ChatRepository,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private val _chatId = MutableStateFlow<String?>(null)
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(ChatDetailState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // TODO: init data logic
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ChatDetailState()
        )

    fun onAction(action: ChatDetailAction) {
        when (action) {
            is ChatDetailAction.OnSelectChat -> switchChat(action.chatId)
            else -> Unit
        }
    }

    private fun switchChat(chatId: String?) = with(viewModelScope) {
        _chatId.update { chatId }
        launch {
            chatId?.let { id ->
                chatRepository.fetchChatById(id)
            }
        }
    }
}
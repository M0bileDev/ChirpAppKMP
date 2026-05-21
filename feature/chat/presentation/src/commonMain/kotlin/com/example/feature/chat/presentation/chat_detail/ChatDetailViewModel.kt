@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.feature.chat.presentation.chat_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.util.onFailure
import com.example.core.domain.util.onSuccess
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.presentation.mappers.toUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
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

    private val chatInfoFlow = _chatId
        .flatMapLatest { chatId ->
            if (chatId != null) {
                chatRepository.getChatInfoById(chatId)
            } else emptyFlow()
        }

    private val stateWithMessages = combine(
        _state,
        chatInfoFlow,
        sessionStorage.observeAuthInfo()
    ) { currentState, chatInfo, authInfo ->
        if (authInfo == null) return@combine ChatDetailState()

        currentState.copy(
            chatUi = chatInfo.chat.toUi(localParticipantId = authInfo.user.id)
        )
    }
    val state = _chatId
        .flatMapLatest { chatId ->
            if (chatId != null) {
                stateWithMessages
            } else {
                _state
            }
        }
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
            ChatDetailAction.OnChatOptionsClick -> chatOptionsClick()
            ChatDetailAction.OnDismissChatOptions -> dismissChatOptions()
            ChatDetailAction.OnLeaveChatClick -> onLeaveChatClick()
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

    private fun chatOptionsClick() {
        _state.update {
            it.copy(
                isChatOptionsOpen = true
            )
        }
    }

    private fun dismissChatOptions() {
        _state.update {
            it.copy(
                isChatOptionsOpen = false
            )
        }
    }

    private fun onLeaveChatClick() = with(viewModelScope) {
        val chatId = _chatId.value ?: return@with

        _state.update {
            it.copy(
                isChatOptionsOpen = false
            )
        }

        launch {
            chatRepository
                .leaveChat(chatId)
                .onSuccess { }
                .onFailure { error ->

                }
        }
    }

}
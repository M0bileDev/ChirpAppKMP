@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.feature.chat.presentation.chat_detail

import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.util.onFailure
import com.example.core.domain.util.onSuccess
import com.example.core.presentation.ext.toUiText
import com.example.feature.chat.domain.chat.ChatConnectionClient
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.domain.message.MessageRepository
import com.example.feature.chat.domain.model.ConnectionState
import com.example.feature.chat.presentation.mappers.toUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatDetailViewModel(
    private val chatRepository: ChatRepository,
    private val sessionStorage: SessionStorage,
    private val messageRepository: MessageRepository,
    private val connectionClient: ChatConnectionClient
) : ViewModel() {

    private val eventChannel = Channel<ChatDetailEvent>()
    val events = eventChannel.receiveAsFlow()

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
                observeConnectionState()
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
                .onSuccess {
                    _state.value.messageTextFieldState.clearText()
                    _chatId.update { null }
                    _state.update {
                        it.copy(
                            chatUi = null,
                            messages = emptyList(),
                            bannerState = BannerState()
                        )
                    }
                }
                .onFailure { error ->
                    eventChannel.send(
                        ChatDetailEvent.OnError(
                            error.toUiText()
                        )
                    )
                }
        }
    }

    private fun observeConnectionState() {
        connectionClient
            .connectionState
            .onEach { connectionState ->
                if (connectionState == ConnectionState.CONNECTED) {
                    _chatId.value?.let { chatId ->
                        //before = null, most recent page of messages
                        messageRepository.fetchMessages(chatId = chatId, before = null)
                    }

                    _state.update {
                        it.copy(
                            connectionState = connectionState
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}
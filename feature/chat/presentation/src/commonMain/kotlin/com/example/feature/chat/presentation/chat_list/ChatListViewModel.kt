package com.example.feature.chat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.auth.SessionStorage
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.presentation.mappers.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val chatRepository: ChatRepository,
    sessionStorage: SessionStorage
) : ViewModel() {
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(ChatListState())
    val state = combine(
        _state,
        chatRepository.getChats(),
        sessionStorage.observeAuthInfo()
    ) { currentState, chats, authInfo ->
        if (authInfo == null) return@combine ChatListState()

        currentState.copy(
            chats = chats.map { it.toUi(authInfo.user.id) },
            localParticipant = authInfo.user.toUi()
        )
    }
        .onStart {
            if (!hasLoadedInitialData) {
                loadChats()
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ChatListState()
        )

    fun onAction(chatListAction: ChatListAction) {
        when (chatListAction) {
            is ChatListAction.OnSelectChat -> {
                _state.update {
                    it.copy(
                        selectedChatId = chatListAction.chatId
                    )
                }
            }

            ChatListAction.OnUserAvatarClick -> {
                _state.update {
                    it.copy(
                        isUserManuOpen = true
                    )
                }
            }

            ChatListAction.OnLogoutClick -> showLogoutConfirmation()
            ChatListAction.OnProfileSettingsClick,
            ChatListAction.OnDismissUserMenu -> {
                _state.update {
                    it.copy(
                        isUserManuOpen = false
                    )
                }
            }

            else -> Unit
        }
    }

    private fun showLogoutConfirmation() {
        _state.update {
            it.copy(
                showLogoutConfirmation = true
            )
        }
    }

    private fun loadChats() = with(viewModelScope) {
        launch {
            chatRepository.fetchChats()
        }
    }
}
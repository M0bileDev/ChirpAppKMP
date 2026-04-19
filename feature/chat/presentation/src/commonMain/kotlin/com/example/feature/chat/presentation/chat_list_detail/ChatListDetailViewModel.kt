package com.example.feature.chat.presentation.chat_list_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatListDetailViewModel : ViewModel() {

    private var _state = MutableStateFlow(ChatListDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: ChatListDetailAction) {
        when (action) {
            is ChatListDetailAction.OnChatClick -> TODO()
            ChatListDetailAction.OnCreateChatClick -> TODO()
            ChatListDetailAction.OnDismissCurrentDialog -> TODO()
            ChatListDetailAction.OnManageChatClick -> TODO()
            ChatListDetailAction.OnProfileSettingsClick -> TODO()
        }
    }


}
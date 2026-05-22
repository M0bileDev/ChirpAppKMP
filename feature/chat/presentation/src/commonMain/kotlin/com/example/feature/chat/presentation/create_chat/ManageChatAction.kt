package com.example.feature.chat.presentation.create_chat

sealed interface ManageChatAction {
    data object OnAddClick : ManageChatAction
    data object OnDismissDialog : ManageChatAction
    data object OnCreateChatClick : ManageChatAction
}
package com.example.feature.chat.presentation.manage_chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ManageChatViewModel : ViewModel() {

    private val _eventChannel = Channel<ManageChatEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ManageChatState())
    val state = _state.asStateFlow()

    fun onAction(manageChatAction: ManageChatAction) {}
}
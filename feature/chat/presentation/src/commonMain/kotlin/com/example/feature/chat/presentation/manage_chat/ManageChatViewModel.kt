package com.example.feature.chat.presentation.manage_chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ManageChatViewModel : ViewModel() {

    private val _state = MutableStateFlow(ManageChatState())
    val state = _state.asStateFlow()

    fun onAction(manageChatAction: ManageChatAction) {}
}
package com.example.feature.chat.presentation.chat_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ChatDetailViewModel : ViewModel() {
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

    fun onAction(chatDetailAction: ChatDetailAction) {}
}
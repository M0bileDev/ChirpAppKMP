package com.example.feature.chat.presentation.create_chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class CreateChatViewModel(
) : ViewModel() {
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(CreateChatState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // TODO: implement
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CreateChatState()
        )

    fun onAction(createChatAction: CreateChatAction) {}
}
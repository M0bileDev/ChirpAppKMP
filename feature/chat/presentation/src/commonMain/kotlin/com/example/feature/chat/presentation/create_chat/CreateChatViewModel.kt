@file:OptIn(FlowPreview::class)

package com.example.feature.chat.presentation.create_chat

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

class CreateChatViewModel(
) : ViewModel() {
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(CreateChatState())
    private val searchFlow = snapshotFlow { state.value.queryTextState.text.toString() }
        .debounce(1.seconds)
        .onEach { query ->
            performSearch(query)
        }

    private fun performSearch(query: String) {
        if(query.isBlank()){
            _state.update { it.copy(
                currentSearchResult = null,
                canAddParticipant = false,
                searchError = null
            ) }
            return
        }
    }

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
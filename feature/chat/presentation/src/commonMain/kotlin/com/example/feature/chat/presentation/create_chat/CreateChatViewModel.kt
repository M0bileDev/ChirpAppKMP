@file:OptIn(FlowPreview::class)

package com.example.feature.chat.presentation.create_chat

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.error_participant_not_found
import com.example.core.domain.util.DataError
import com.example.core.domain.util.onFailure
import com.example.core.domain.util.onSuccess
import com.example.core.presentation.ext.toUiText
import com.example.core.presentation.util.UiText
import com.example.feature.chat.domain.chat.ChatParticipantService
import com.example.feature.chat.mappers.toUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class CreateChatViewModel(
    private val chatParticipantService: ChatParticipantService
) : ViewModel() {
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(CreateChatState())
    private val searchFlow = snapshotFlow { _state.value.queryTextState.text.toString() }
        .debounce(1.seconds)
        .onEach { query ->
            performSearch(query)
        }
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                searchFlow.launchIn(viewModelScope)
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CreateChatState()
        )

    private fun performSearch(query: String) = with(viewModelScope) {
        if (query.isBlank()) {
            _state.update {
                it.copy(
                    currentSearchResult = null,
                    canAddParticipant = false,
                    searchError = null
                )
            }
            return@with
        }

        launch {
            _state.update {
                it.copy(
                    isSearching = true,
                    canAddParticipant = false
                )
            }

            chatParticipantService
                .searchParticipant(query)
                .onSuccess { participant ->
                    _state.update {
                        it.copy(
                            currentSearchResult = participant.toUi(),
                            isSearching = false,
                            canAddParticipant = true,
                            searchError = null
                        )
                    }
                }
                .onFailure { error ->
                    val errorMessage = when (error) {
                        DataError.Remote.NOT_FOUND -> UiText.Resource(Res.string.error_participant_not_found)
                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            currentSearchResult = null,
                            isSearching = false,
                            canAddParticipant = false,
                            searchError = errorMessage
                        )
                    }
                }

        }

    }

    fun onAction(createChatAction: CreateChatAction) {
        when (createChatAction) {
            CreateChatAction.OnAddClick -> addParticipant()
            CreateChatAction.OnCreateChatClick -> Unit
            CreateChatAction.OnDismissDialog -> Unit
        }
    }

    private fun addParticipant() {
        state.value.currentSearchResult?.let { participant ->
            val isAlreadyPartOfChat = state.value.selectedChatParticipants.any {
                it.id == participant.id
            }

            if (isAlreadyPartOfChat) return@let

            _state.update {
                it.copy(
                    selectedChatParticipants = it.selectedChatParticipants + participant,
                    canAddParticipant = false,
                    currentSearchResult = null
                )
            }

            state.value.queryTextState.clearText()
        }
    }
}
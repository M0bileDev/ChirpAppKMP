@file:OptIn(FlowPreview::class)

package com.example.feature.chat.presentation.manage_chat

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
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.presentation.mappers.toUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ManageChatViewModel(
    private val chatRepository: ChatRepository,
    private val chatParticipantService: ChatParticipantService
) : ViewModel() {
    private val _chatId = MutableStateFlow<String?>(null)
    private val _eventChannel = Channel<ManageChatEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ManageChatState())
    val state = _state.asStateFlow()

    private val searchFlow = snapshotFlow { _state.value.queryTextState.text.toString() }
        .debounce(1.seconds)
        .onEach { query ->
            performSearch(query)
        }

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


    fun onAction(manageChatAction: ManageChatAction) {
        when (manageChatAction) {
            ManageChatAction.OnAddClick -> addParticipantToChat()
            is ManageChatAction.ChatParticipantsAction.OnSelectChat -> onSelectChat(manageChatAction.chatId)
            else -> Unit
        }
    }

    private fun addParticipantToChat() = with(state.value) {
        currentSearchResult?.let { participantFromSearch ->
            val isAlreadySelected = selectedChatParticipants.any {
                it.id == participantFromSearch.id
            }
            val isAlreadyInChat = existingChatParticipants.any {
                it.id == participantFromSearch.id
            }

            if (isAlreadySelected || isAlreadyInChat) return@with

            val updatedParticipants = selectedChatParticipants + participantFromSearch
            _state.update {
                it.copy(
                    selectedChatParticipants = updatedParticipants,
                    canAddParticipant = false,
                    currentSearchResult = null
                )
            }.also {
                state.value.queryTextState.clearText()
            }
        }
    }

    private fun onSelectChat(chatId: String) {
        _chatId.update { chatId }
    }
}
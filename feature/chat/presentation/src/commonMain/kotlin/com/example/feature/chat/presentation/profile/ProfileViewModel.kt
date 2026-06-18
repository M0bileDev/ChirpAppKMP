package com.example.feature.chat.presentation.profile

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.validation.PasswordValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(ProfileState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeCanChangePassword()
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProfileState()
        )

    private fun observeCanChangePassword() {
        val isCurrentPasswordValidFlow = snapshotFlow {
            state.value.currentPasswordTextState.text.toString()
        }.map { currentPassword ->
            currentPassword.isNotBlank()
        }.distinctUntilChanged()

        val isNewPasswordValidFlow = snapshotFlow {
            state.value.newPasswordTextState.text.toString()
        }.map { newPassword ->
            PasswordValidator.validate(newPassword).isValidPassword
        }.distinctUntilChanged()

        combine(
            isCurrentPasswordValidFlow,
            isNewPasswordValidFlow
        ) { isCurrentPasswordValid, isNewPasswordValid ->
            _state.update {
                it.copy(
                    canChangePassword = isCurrentPasswordValid && isNewPasswordValid
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(profileAction: ProfileAction) {}
}
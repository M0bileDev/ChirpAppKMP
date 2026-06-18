package com.example.feature.chat.presentation.profile

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.error_current_password_equal_to_new_one
import chirpappkmp.feature.chat.presentation.generated.resources.error_current_password_incorrect
import com.example.core.domain.auth.AuthService
import com.example.core.domain.util.DataError
import com.example.core.domain.util.onFailure
import com.example.core.domain.util.onSuccess
import com.example.core.domain.validation.PasswordValidator
import com.example.core.presentation.ext.toUiText
import com.example.core.presentation.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: AuthService
) : ViewModel() {
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

    fun onAction(profileAction: ProfileAction) {
        when (profileAction) {
            is ProfileAction.OnChangePasswordClick -> changePassword()
            else -> Unit
        }
    }

    private fun changePassword() {
        if (!state.value.canChangePassword || state.value.isChangingPassword) return

        _state.update {
            it.copy(
                isChangingPassword = true
            )
        }

        val currentPassword = state.value.currentPasswordTextState.text.toString()
        val newPassword = state.value.newPasswordTextState.text.toString()
        viewModelScope.launch {
            authService.changePassword(
                currentPassword = currentPassword,
                newPassword = newPassword
            ).onSuccess {
                state.value.currentPasswordTextState.clearText()
                state.value.newPasswordTextState.clearText()

                _state.update {
                    it.copy(
                        newPasswordError = null
                    )
                }
            }.onFailure { error ->
                val errorMessage = when (error) {
                    DataError.Remote.UNAUTHORIZED -> {
                        UiText.Resource(Res.string.error_current_password_incorrect)
                    }

                    DataError.Remote.CONFLICT -> {
                        UiText.Resource(Res.string.error_current_password_equal_to_new_one)
                    }

                    else -> error.toUiText()
                }
                _state.update {
                    it.copy(
                        newPasswordError = errorMessage
                    )
                }
            }
        }


        _state.update {
            it.copy(
                isNewPasswordVisible = false,
                isCurrentPasswordVisible = false,
                isChangingPassword = false
            )
        }
    }
}
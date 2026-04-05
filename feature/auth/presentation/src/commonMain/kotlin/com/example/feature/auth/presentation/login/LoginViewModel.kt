package com.example.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirpappkmp.feature.auth.presentation.generated.resources.Res
import chirpappkmp.feature.auth.presentation.generated.resources.error_email_not_verified
import chirpappkmp.feature.auth.presentation.generated.resources.error_invalid_credentials
import com.example.core.domain.auth.AuthService
import com.example.core.domain.util.DataError
import com.example.core.domain.util.onFailure
import com.example.core.domain.util.onSuccess
import com.example.core.presentation.ext.toUiText
import com.example.core.presentation.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                //todo add loading content
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LoginState()
        )

    fun onAction(loginAction: LoginAction) {
        when (loginAction) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibilityClick -> TODO()
            else -> Unit
        }
    }

    private fun login() = with(viewModelScope) {
        if (!state.value.canLogin) return@with

        launch {
            _state.update {
                it.copy(
                    isLoggingIn = true
                )
            }

            val email = state.value.emailTextFieldState.text.toString()
            val password = state.value.passwordTextFieldState.text.toString()

            authService.login(
                email = email,
                password = password
            ).onSuccess { authInfo ->
                _state.update {
                    it.copy(
                        isLoggingIn = false
                    )
                }
                eventChannel.send(LoginEvent.SuccessLogin)
            }.onFailure { error ->
                val errorMessage = when (error) {
                    DataError.Remote.UNAUTHORIZED -> UiText.Resource(Res.string.error_invalid_credentials)
                    DataError.Remote.FORBIDDEN -> UiText.Resource(Res.string.error_email_not_verified)
                    else -> error.toUiText()
                }
                _state.update {
                    it.copy(
                        error = errorMessage,
                        isLoggingIn = false
                    )
                }
            }
        }
    }
}
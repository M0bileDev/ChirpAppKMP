package com.example.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirpappkmp.feature.auth.presentation.generated.resources.Res
import chirpappkmp.feature.auth.presentation.generated.resources.error_account_exists
import chirpappkmp.feature.auth.presentation.generated.resources.error_invalid_email
import chirpappkmp.feature.auth.presentation.generated.resources.error_invalid_password
import chirpappkmp.feature.auth.presentation.generated.resources.error_invalid_username
import com.example.core.domain.auth.AuthService
import com.example.core.domain.util.DataError
import com.example.core.domain.util.onFailure
import com.example.core.domain.util.onSuccess
import com.example.core.domain.validation.PasswordValidator
import com.example.core.presentation.ext.toUiText
import com.example.core.presentation.util.UiText
import com.example.feature.auth.domain.EmailValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authService: AuthService
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(registerAction: RegisterAction) {
        when (registerAction) {
            RegisterAction.OnLoginClick -> validateFormInputs()
            RegisterAction.OnInputTextFocusGain -> clearAllTextFieldErrors()
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> {}
        }
    }

    private fun register() {
        if (!validateFormInputs()) {
            return
        }

        _state.update {
            it.copy(
                isRegistering = true
            )
        }

        viewModelScope.launch {

            val currentState = state.value
            val email = currentState.emailTextState.text.toString()
            val username = currentState.usernameTextState.text.toString()
            val password = currentState.passwordTextState.text.toString()

            authService.register(
                email = email,
                username = username,
                password = password
            ).onSuccess {
                _state.update {
                    it.copy(
                        isRegistering = false
                    )
                }

            }.onFailure { error ->
                val registrationError = when (error) {
                    DataError.Remote.CONFLICT -> UiText.Resource(Res.string.error_account_exists)
                    else -> error.toUiText()
                }
                _state.update {
                    it.copy(
                        isRegistering = false,
                        registrationError = registrationError
                    )
                }
            }
        }
    }

    private fun clearAllTextFieldErrors() {
        _state.update {
            it.copy(
                emailError = null,
                usernameError = null,
                passwordError = null,
                registrationError = null
            )
        }
    }

    private fun validateFormInputs(): Boolean {
        val currentState = state.value
        val email = currentState.emailTextState.text.toString()
        val username = currentState.usernameTextState.text.toString()
        val password = currentState.passwordTextState.text.toString()

        val isEmailValid = EmailValidator.validate(email)
        val passwordValidationState = PasswordValidator.validate(password)
        val isUsernameValid = username.length in 3..20

        val emailError = if (!isEmailValid) {
            UiText.Resource(Res.string.error_invalid_email)
        } else null

        val usernameError = if (!isUsernameValid) {
            UiText.Resource(Res.string.error_invalid_username)
        } else null

        val passwordError = if (!passwordValidationState.isValidPassword) {
            UiText.Resource(Res.string.error_invalid_password)
        } else null

        _state.update {
            it.copy(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError
            )
        }

        return isEmailValid && passwordValidationState.isValidPassword && isUsernameValid
    }
}
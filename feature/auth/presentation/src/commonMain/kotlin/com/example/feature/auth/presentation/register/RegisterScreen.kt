package com.example.feature.auth.presentation.register

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import chirpappkmp.feature.auth.presentation.generated.resources.Res
import chirpappkmp.feature.auth.presentation.generated.resources.email
import chirpappkmp.feature.auth.presentation.generated.resources.email_placeholder
import chirpappkmp.feature.auth.presentation.generated.resources.login
import chirpappkmp.feature.auth.presentation.generated.resources.password
import chirpappkmp.feature.auth.presentation.generated.resources.password_hint
import chirpappkmp.feature.auth.presentation.generated.resources.register
import chirpappkmp.feature.auth.presentation.generated.resources.username
import chirpappkmp.feature.auth.presentation.generated.resources.username_hint
import chirpappkmp.feature.auth.presentation.generated.resources.username_placeholder
import chirpappkmp.feature.auth.presentation.generated.resources.welcome_to_chirp
import com.example.core.designsystem.components.brand.ChirpBrandLogo
import com.example.core.designsystem.components.buttons.ChirpButton
import com.example.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.example.core.designsystem.components.layouts.ChirpSnackbarScaffoldLayout
import com.example.core.designsystem.components.textfields.ChirpPasswordTextField
import com.example.core.designsystem.components.textfields.ChirpTextField
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    snackbarHostState: SnackbarHostState,
    onAction: (RegisterAction) -> Unit
) = with(state) {
    ChirpSnackbarScaffoldLayout(snackbarHostState = snackbarHostState) {
        ChirpAdaptiveFormLayout(
            headerText = stringResource(Res.string.welcome_to_chirp),
            errorText = registrationError?.asString(),
            logo = {
                ChirpBrandLogo()
            },
            formContent = {
                ChirpTextField(
                    state = usernameTextState,
                    placeholder = stringResource(Res.string.username_placeholder),
                    title = stringResource(Res.string.username),
                    supportingText = usernameError?.asString()
                        ?: stringResource(Res.string.username_hint),
                    isError = usernameError != null,
                    onFocusChanged = { isFocused ->
                        onAction(RegisterAction.OnInputTextFocusGain)
                    }
                )
                ChirpTextField(
                    state = emailTextState,
                    placeholder = stringResource(Res.string.email_placeholder),
                    title = stringResource(Res.string.email),
                    supportingText = emailError?.asString(),
                    isError = emailError != null,
                    onFocusChanged = { isFocused ->
                        onAction(RegisterAction.OnInputTextFocusGain)
                    }
                )
                ChirpPasswordTextField(
                    state = passwordTextState,
                    placeholder = stringResource(Res.string.password),
                    title = stringResource(Res.string.password),
                    supportingText = passwordError?.asString()
                        ?: stringResource(Res.string.password_hint),
                    isError = passwordError != null,
                    onFocusChanged = { isFocused ->
                        onAction(RegisterAction.OnInputTextFocusGain)
                    },
                    onToggleVisibilityClick = {
                        onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                    },
                    isPasswordVisible = isPasswordVisible
                )
                ChirpButton(
                    text = stringResource(Res.string.register),
                    onClick = {
                        onAction(RegisterAction.OnRegisterClick)
                    },
                    enabled = canRegister,
                    isLoading = isRegistering
                )
                ChirpButton(
                    text = stringResource(Res.string.login),
                    onClick = {
                        onAction(RegisterAction.OnLoginClick)
                    }
                )

            }
        )
    }
}

@Preview
@Composable
private fun PreviewRegisterScreen() {
    ChirpTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}
package com.example.feature.auth.presentation.register_success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import chirpappkmp.feature.auth.presentation.generated.resources.Res
import chirpappkmp.feature.auth.presentation.generated.resources.account_successfully_created
import chirpappkmp.feature.auth.presentation.generated.resources.login
import chirpappkmp.feature.auth.presentation.generated.resources.resend_verification_email
import chirpappkmp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import com.example.core.designsystem.components.brand.ChirpSuccessIcon
import com.example.core.designsystem.components.buttons.ChirpButton
import com.example.core.designsystem.components.buttons.ChirpButtonStyle
import com.example.core.designsystem.components.layouts.ChirpAdaptiveResultLayout
import com.example.core.designsystem.components.layouts.ChirpSimpleSuccessLayout
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessRoot(
    viewModel: RegisterSuccessViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterSuccessScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterSuccessScreen(
    state: RegisterSuccessState,
    onAction: (RegisterSuccessAction) -> Unit
) = with(state) {
    ChirpAdaptiveResultLayout {
        ChirpSimpleSuccessLayout(
            title = stringResource(Res.string.account_successfully_created),
            description = stringResource(Res.string.verification_email_sent_to_x, registeredEmail),
            icon = {
                ChirpSuccessIcon()
            },
            primaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.login),
                    onClick = {
                        onAction(RegisterSuccessAction.OnLoginClick)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.resend_verification_email),
                    onClick = {
                        onAction(RegisterSuccessAction.OnResendVerificationEmailClick)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isResendingVerificationEmail,
                    isLoading = isResendingVerificationEmail,
                    style = ChirpButtonStyle.SECONDARY
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewRegisterSuccessScreen() {
    ChirpTheme {
        RegisterSuccessScreen(
            state = RegisterSuccessState(
                registeredEmail = "lorem@impsum.internet"
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewDarkRegisterSuccessScreen() {
    ChirpTheme(
        darkTheme = true
    ) {
        RegisterSuccessScreen(
            state = RegisterSuccessState(
                registeredEmail = "lorem@impsum.internet"
            ),
            onAction = {}
        )
    }
}


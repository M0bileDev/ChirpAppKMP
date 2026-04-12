package com.example.feature.auth.presentation.reset_password

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirpappkmp.feature.auth.presentation.generated.resources.Res
import chirpappkmp.feature.auth.presentation.generated.resources.new_password
import chirpappkmp.feature.auth.presentation.generated.resources.password
import chirpappkmp.feature.auth.presentation.generated.resources.password_hint
import chirpappkmp.feature.auth.presentation.generated.resources.reset_password_success
import chirpappkmp.feature.auth.presentation.generated.resources.submit
import com.example.core.designsystem.components.brand.ChirpBrandLogo
import com.example.core.designsystem.components.buttons.ChirpButton
import com.example.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.example.core.designsystem.components.layouts.ChirpSnackbarScaffoldLayout
import com.example.core.designsystem.components.textfields.ChirpPasswordTextField
import com.example.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordRoot(
    viewModel: ResetPasswordViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ResetPasswordScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction
    )
}

@Composable
fun ResetPasswordScreen(
    state: ResetPasswordState,
    snackbarHostState: SnackbarHostState,
    onAction: (ResetPasswordAction) -> Unit
) = with(state) {
    ChirpSnackbarScaffoldLayout(
        snackbarHostState = snackbarHostState
    ) {
        ChirpAdaptiveFormLayout(
            headerText = stringResource(Res.string.new_password),
            errorText = errorText?.asString(),
            logo = {
                ChirpBrandLogo()
            },
            formContent = {
                ChirpPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = passwordTextState,
                    placeholder = stringResource(Res.string.password),
                    title = stringResource(Res.string.password),
                    supportingText = stringResource(Res.string.password_hint),
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibilityClick = {
                        onAction(ResetPasswordAction.OnTogglePassword)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ChirpButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.submit),
                    onClick = {
                        onAction(ResetPasswordAction.OnSubmitClick)
                    },
                    isLoading = isLoading,
                    enabled = !isLoading && canSubmit
                )
                if (isResetSuccessful) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(Res.string.reset_password_success),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.extended.success,
                    )
                }
            }
        )
    }
}
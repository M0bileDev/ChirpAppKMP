package com.example.feature.auth.presentation.reset_password

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.components.layouts.ChirpSnackbarScaffoldLayout
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
    ) {}
}
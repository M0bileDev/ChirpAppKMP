package com.example.feature.chat.presentation.create_chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateChatRoot(
    viewModel: CreateChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CreateChatScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CreateChatScreen(
    state: CreateChatState,
    onAction: (CreateChatAction) -> Unit
) = with(state) {}

@Preview
@Composable
fun PreviewCreateChatScreen() {
    ChirpTheme {
        CreateChatScreen(
            CreateChatState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
fun PreviewDarkCreateChatScreen() {
    ChirpTheme(darkTheme = true) {
        CreateChatScreen(
            CreateChatState(),
            onAction = {}
        )
    }
}

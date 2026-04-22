package com.example.feature.chat.presentation.create_chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.theme.ChirpTheme
import com.example.core.presentation.util.clearFocusOnTap
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
) = with(state) {


    Column(
        modifier = Modifier
            .clearFocusOnTap()
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
            .background(MaterialTheme.colorScheme.surface)
    ) {

    }
}

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

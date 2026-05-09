@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.chat_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ChatDetailRoot(
    viewModel: ChatDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatDetailScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ChatDetailScreen(
    state: ChatDetailState,
    onAction: (ChatDetailAction) -> Unit
) = with(state) {
}

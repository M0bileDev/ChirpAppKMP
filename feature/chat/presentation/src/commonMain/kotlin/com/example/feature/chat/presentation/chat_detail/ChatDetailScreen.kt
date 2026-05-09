@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.chat_detail

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.theme.extended
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
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
    val configuration = currentDeviceConfiguration()
    val containerColor = if(configuration.isMobile){
        MaterialTheme.colorScheme.surface
    }else{
        MaterialTheme.colorScheme.extended.surfaceLower
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = containerColor
    ) {

    }
}

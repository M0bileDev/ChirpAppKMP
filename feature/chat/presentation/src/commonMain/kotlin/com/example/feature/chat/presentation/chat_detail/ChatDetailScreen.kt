@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.chat_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.theme.extended
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.clearFocusOnTap
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
    val containerColor = if (!configuration.isWideScreen) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.extended.surfaceLower
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = containerColor
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .clearFocusOnTap()
                .padding(innerPadding)
                .then(
                    if (configuration.isWideScreen) {
                        Modifier.padding(horizontal = 8.dp)
                    } else Modifier
                )
        ) {

        }
    }
}

@Composable
private fun DynamicRoundedCorner(
    isCornersRounded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val shape = if (isCornersRounded) RoundedCornerShape(16.dp) else RectangleShape

    Box(
        modifier = modifier
            .shadow(
                elevation = if (isCornersRounded) 4.dp else 0.dp,
                shape = shape
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = shape
            )
    ) {
        content()
    }
}

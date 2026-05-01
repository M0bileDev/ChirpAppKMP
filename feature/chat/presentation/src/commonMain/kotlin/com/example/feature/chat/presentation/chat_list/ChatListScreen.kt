@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.chat_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.create_chat
import com.example.core.designsystem.components.buttons.ChirpFloatingActionButton
import com.example.core.designsystem.theme.extended
import com.example.feature.chat.presentation.chat_list.components.ChatListHeader
import com.example.feature.chat.presentation.chat_list.components.EmptyChatSection
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ChatListRoot(
    viewModel: ChatListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ChatListScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction
    )
}

@Composable
fun ChatListScreen(
    state: ChatListState,
    snackbarHostState: SnackbarHostState,
    onAction: (ChatListAction) -> Unit
) = with(state) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.extended.surfaceLower,
        contentWindowInsets = WindowInsets.safeDrawing,
        floatingActionButton = {
            ChirpFloatingActionButton(
                onClick = {
                    onAction(ChatListAction.OnCreateChatClick)
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(Res.string.create_chat)
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ChatListHeader(
                localParticipant = localParticipant,
                isUserMenuOpen = isUserManuOpen,
                onUserAvatarClick = {
                    onAction(ChatListAction.OnUserAvatarClick)
                },
                onDismissMenu = {
                    onAction(ChatListAction.OnDismissUserMenu)
                },
                onProfileSettingsClick = {
                    onAction(ChatListAction.OnProfileSettingsClick)
                },
                onLogoutClick = {
                    onAction(ChatListAction.OnLogoutClick)
                },
            )
            if (chats.isEmpty() && !isLoading) {
                EmptyChatSection(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp
                        )
                )
            }
        }
    }
}

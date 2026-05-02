@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.example.feature.chat.presentation.chat_list_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.theme.extended
import com.example.core.presentation.util.DialogSheetScopedViewModel
import com.example.feature.chat.presentation.chat_list.ChatListRoot
import com.example.feature.chat.presentation.create_chat.CreateChatRoot
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListDetailAdaptiveLayout(
    onLogout: () -> Unit,
    viewModel: ChatListDetailViewModel = koinViewModel<ChatListDetailViewModel>()
) {
    val sharedState by viewModel.state.collectAsStateWithLifecycle()
    val paneScaffoldDirective = createPaneScaffoldDirectiveByConfigurationAndAdaptiveInfo()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = paneScaffoldDirective
    )
    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = scaffoldNavigator.canNavigateBack()
    ) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }

    ListDetailPaneScaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.extended.surfaceLower),
        directive = paneScaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                ChatListRoot(
                    onChatClick = { chat ->
                        viewModel.onAction(ChatListDetailAction.OnChatClick(chat.id))
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                        }
                    },
                    onLogout = onLogout,
                    onCreateChatClick = {},
                    onProfileSettingsClick = {},
                )
            }
        },
        detailPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    sharedState.selectedChatId?.let { chatId ->
                        Text(
                            text = "Chat $chatId"
                        )
                    }
                }
            }
        },
    )

    DialogSheetScopedViewModel(
        visible = sharedState.dialogState is DialogState.CreateChat
    ) {
        CreateChatRoot(
            onDismiss = {
                viewModel.onAction(ChatListDetailAction.OnDismissCurrentDialog)
            },
            onChatCreated = { chat ->
                viewModel.onAction(ChatListDetailAction.OnDismissCurrentDialog)
                viewModel.onAction(ChatListDetailAction.OnChatClick(chat.id))
                scope.launch {
                    scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            }
        )
    }
}
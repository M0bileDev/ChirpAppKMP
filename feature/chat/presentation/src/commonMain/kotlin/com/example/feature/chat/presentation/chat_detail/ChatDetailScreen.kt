@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.chat_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.theme.extended
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.clearFocusOnTap
import com.example.feature.chat.presentation.chat_detail.components.ChatDetailHeader
import com.example.feature.chat.presentation.chat_detail.components.MessageBox
import com.example.feature.chat.presentation.chat_detail.components.MessageList
import com.example.feature.chat.presentation.components.ChatHeader
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ChatDetailRoot(
    isDetailPresent: Boolean,
    viewModel: ChatDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatDetailScreen(
        state = state,
        isDetailPresent = isDetailPresent,
        onAction = viewModel::onAction
    )
}

@Composable
fun ChatDetailScreen(
    state: ChatDetailState,
    isDetailPresent: Boolean,
    onAction: (ChatDetailAction) -> Unit
) = with(state) {
    val configuration = currentDeviceConfiguration()
    val containerColor = if (!configuration.isWideScreen) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.extended.surfaceLower
    }
    val messageLazyListState = rememberLazyListState()

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DynamicRoundedCornerColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    isCornersRounded = configuration.isWideScreen
                ) {
                    ChatHeader {
                        ChatDetailHeader(
                            modifier = Modifier.fillMaxWidth(),
                            chatUi = chatUi,
                            isDetailPresent = isDetailPresent,
                            isChatOptionsDropDownOpen = isChatOptionsOpen,
                            onChatOptionsClick = {
                                onAction(ChatDetailAction.OnChatOptionsCLick)
                            },
                            onDismissChatOptions = {
                                onAction(ChatDetailAction.OnDismissChatOptions)
                            },
                            onManageChatClick = {
                                onAction(ChatDetailAction.OnChatMembersClick)
                            },
                            onLeaveChatClick = {
                                onAction(ChatDetailAction.OnLeaveChatClick)
                            },
                            onBackClick = {
                                onAction(ChatDetailAction.OnBackClick)
                            }
                        )
                    }

                    MessageList(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        messages = messages,
                        listState = messageLazyListState,
                        onMessageLongClick = { message ->
                            onAction(ChatDetailAction.OnMessageLongClick(message))
                        },
                        onMessageRetryClick = { message ->
                            onAction(ChatDetailAction.OnRetryClick(message))
                        },
                        onDismissMessageMenu = {
                            onAction(ChatDetailAction.OnDismissMessageMenu)
                        },
                        onDeleteMessageClick = { message ->
                            onAction(ChatDetailAction.OnDeleteMessageClick(message))
                        }
                    )

                    AnimatedVisibility(
                        visible = !configuration.isWideScreen && chatUi != null
                    ) {
                        MessageBox(
                            modifier = Modifier.fillMaxWidth(),
                            messageTextFieldState = messageTextFieldState,
                            isTextInputEnabled = canSendMessage,
                            connectionState = connectionState,
                            onSendClick = {
                                onAction(ChatDetailAction.OnSendMessageClick)
                            }
                        )
                    }
                }

                if(configuration.isWideScreen){
                    Spacer(modifier = Modifier.height(8.dp))
                }

                AnimatedVisibility(
                    visible = configuration.isWideScreen && chatUi != null
                ){
                    MessageBox(
                        modifier = Modifier.fillMaxWidth(),
                        messageTextFieldState = messageTextFieldState,
                        isTextInputEnabled = canSendMessage,
                        connectionState = connectionState,
                        onSendClick = {
                            onAction(ChatDetailAction.OnSendMessageClick)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DynamicRoundedCornerColumn(
    isCornersRounded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = if (isCornersRounded) RoundedCornerShape(16.dp) else RectangleShape

    Column(
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

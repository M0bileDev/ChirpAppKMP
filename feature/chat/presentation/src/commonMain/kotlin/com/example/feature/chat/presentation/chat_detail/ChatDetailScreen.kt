@file:OptIn(ExperimentalUuidApi::class, ExperimentalComposeUiApi::class)

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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.theme.ChirpTheme
import com.example.core.designsystem.theme.extended
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.ObserveAsEvents
import com.example.core.presentation.util.UiText
import com.example.core.presentation.util.clearFocusOnTap
import com.example.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.example.feature.chat.presentation.chat_detail.components.ChatDetailHeader
import com.example.feature.chat.presentation.chat_detail.components.MessageBox
import com.example.feature.chat.presentation.chat_detail.components.MessageList
import com.example.feature.chat.presentation.components.ChatHeader
import com.example.feature.chat.presentation.model.ChatUi
import com.example.feature.chat.presentation.model.MessageUi
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ChatDetailRoot(
    chatId: String?,
    isDetailPresent: Boolean,
    onBack: () -> Unit,
    viewModel: ChatDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ChatDetailEvent.OnChatLeft -> onBack()
            is ChatDetailEvent.OnError -> {
                snackbarState.showSnackbar(event.error.asStringAsync())
            }
        }
    }

    LaunchedEffect(chatId) {
        viewModel.onAction(ChatDetailAction.OnSelectChat(chatId))
    }

    BackHandler(
        enabled = !isDetailPresent
    ) {
        viewModel.onAction(ChatDetailAction.OnSelectChat(null))
        onBack()
    }

    ChatDetailScreen(
        state = state,
        snackbarState = snackbarState,
        isDetailPresent = isDetailPresent,
        onAction = viewModel::onAction
    )
}

@Composable
fun ChatDetailScreen(
    state: ChatDetailState,
    snackbarState: SnackbarHostState,
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
        containerColor = containerColor,
        snackbarHost = {
            SnackbarHost(snackbarState)
        }
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
                                onAction(ChatDetailAction.OnChatOptionsClick)
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 8.dp,
                                    horizontal = 16.dp
                                ),
                            messageTextFieldState = messageTextFieldState,
                            isTextInputEnabled = canSendMessage,
                            connectionState = connectionState,
                            onSendClick = {
                                onAction(ChatDetailAction.OnSendMessageClick)
                            }
                        )
                    }
                }

                if (configuration.isWideScreen) {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                AnimatedVisibility(
                    visible = configuration.isWideScreen && chatUi != null
                ) {
                    DynamicRoundedCornerColumn(
                        isCornersRounded = configuration.isWideScreen
                    ) {
                        MessageBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
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
}

@Composable
private fun DynamicRoundedCornerColumn(
    isCornersRounded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = if (isCornersRounded) RoundedCornerShape(24.dp) else RectangleShape

    Column(
        modifier = modifier
            .shadow(
                elevation = if (isCornersRounded) 8.dp else 0.dp,
                shape = shape,
                spotColor = Color.Black.copy(alpha = 0.2f)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = shape
            )
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewChatDetailScreen() {
    ChirpTheme {
        ChatDetailScreen(
            state = ChatDetailState(),
            snackbarState = SnackbarHostState(),
            isDetailPresent = false,
            onAction = {}
        )
    }
}

@Preview
@Composable
fun PreviewChatDetailScreenMessages() {
    ChirpTheme {
        ChatDetailScreen(
            state = ChatDetailState(
                messageTextFieldState = TextFieldState(
                    initialText = "New message"
                ),
                canSendMessage = true,
                chatUi = ChatUi(
                    id = Uuid.random().toString(),
                    localParticipant = ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum",
                        initials = "LI"
                    ),
                    otherParticipants = listOf(
                        ChatParticipantUi(
                            id = Uuid.random().toString(),
                            username = "Lorem ipsum1",
                            initials = "LI1"
                        )
                    ),
                    lastMessage = null,
                    lastMessageSenderUsername = null
                ),
                messages = (1..20).map {
                    val showLocalMessage = Random.nextBoolean()
                    if (showLocalMessage) {
                        MessageUi.LocalUserMessage(
                            id = Uuid.random().toString(),
                            content = "Lorem ipsum",
                            deliveryStatus = ChatMessageDeliveryStatus.SENT,
                            isMenuOpen = false,
                            formattedSentAt = UiText.DynamicString("01/01/1900 00:00"),
                            canRetry = true
                        )
                    } else {
                        MessageUi.OtherUserMessage(
                            id = Uuid.random().toString(),
                            content = "Lorem ipsum",
                            formattedSentAt = UiText.DynamicString("01/01/1900 00:00"),
                            sender = ChatParticipantUi(
                                id = Uuid.random().toString(),
                                username = "Lorem ipsum 1",
                                initials = "LI1"
                            )
                        )
                    }
                }
            ),
            snackbarState = SnackbarHostState(),
            isDetailPresent = true,
            onAction = {}
        )
    }
}

@Preview
@Composable
fun PreviewDarkChatDetailScreen() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatDetailScreen(
            state = ChatDetailState(),
            snackbarState = SnackbarHostState(),
            isDetailPresent = false,
            onAction = {}
        )
    }
}

@Preview
@Composable
fun PreviewDarkChatDetailScreenMessages() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatDetailScreen(
            state = ChatDetailState(
                messageTextFieldState = TextFieldState(
                    initialText = "New message"
                ),
                canSendMessage = true,
                chatUi = ChatUi(
                    id = Uuid.random().toString(),
                    localParticipant = ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum",
                        initials = "LI"
                    ),
                    otherParticipants = listOf(
                        ChatParticipantUi(
                            id = Uuid.random().toString(),
                            username = "Lorem ipsum1",
                            initials = "LI1"
                        )
                    ),
                    lastMessage = null,
                    lastMessageSenderUsername = null
                ),
                messages = (1..20).map {
                    val showLocalMessage = Random.nextBoolean()
                    if (showLocalMessage) {
                        MessageUi.LocalUserMessage(
                            id = Uuid.random().toString(),
                            content = "Lorem ipsum",
                            deliveryStatus = ChatMessageDeliveryStatus.SENT,
                            isMenuOpen = false,
                            formattedSentAt = UiText.DynamicString("01/01/1900 00:00"),
                            canRetry = true
                        )
                    } else {
                        MessageUi.OtherUserMessage(
                            id = Uuid.random().toString(),
                            content = "Lorem ipsum",
                            formattedSentAt = UiText.DynamicString("01/01/1900 00:00"),
                            sender = ChatParticipantUi(
                                id = Uuid.random().toString(),
                                username = "Lorem ipsum 1",
                                initials = "LI1"
                            )
                        )
                    }
                }
            ),
            snackbarState = SnackbarHostState(),
            isDetailPresent = true,
            onAction = {}
        )
    }
}
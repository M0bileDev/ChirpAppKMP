@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.create_chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.cancel
import chirpappkmp.feature.chat.presentation.generated.resources.create_chat
import com.example.core.designsystem.components.brand.ChirpHorizontalDivider
import com.example.core.designsystem.components.buttons.ChirpButton
import com.example.core.designsystem.components.buttons.ChirpButtonStyle
import com.example.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import com.example.core.designsystem.theme.ChirpTheme
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.DeviceConfiguration
import com.example.core.presentation.util.ObserveAsEvents
import com.example.core.presentation.util.clearFocusOnTap
import com.example.feature.chat.domain.model.Chat
import com.example.feature.chat.presentation.components.ChatParticipantSearchTextSection
import com.example.feature.chat.presentation.components.ChatParticipantsSelectionSection
import com.example.feature.chat.presentation.components.ManageChatButtonSection
import com.example.feature.chat.presentation.components.ManageChatHeaderRow
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun CreateChatRoot(
    onDismiss: () -> Unit,
    onChatCreated: (Chat) -> Unit,
    viewModel: CreateChatViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CreateChatEvent.OnChatCreated -> onChatCreated(event.chat)
        }
    }

    ChirpAdaptiveDialogSheetLayout(
        onDismiss = onDismiss,
    ) {
        CreateChatScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    CreateChatAction.OnDismissDialog -> onDismiss()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
fun CreateChatScreen(
    state: CreateChatState,
    onAction: (CreateChatAction) -> Unit
) = with(state) {

    var isTextFieldFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = imeHeight > 0
    val configuration = currentDeviceConfiguration()

    val shouldHideHeader =
        configuration == DeviceConfiguration.MOBILE_LANDSCAPE && isKeyboardVisible && isTextFieldFocused

    Column(
        modifier = Modifier
            .clearFocusOnTap()
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface)
            .imePadding()
            .navigationBarsPadding()
    ) {
        AnimatedVisibility(
            visible = !shouldHideHeader
        ) {
            Column {
                ManageChatHeaderRow(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(Res.string.create_chat),
                    onCloseClick = {
                        onAction(CreateChatAction.OnDismissDialog)
                    }
                )
                ChirpHorizontalDivider()
            }
        }
        ChatParticipantSearchTextSection(
            modifier = Modifier.fillMaxWidth(),
            queryState = queryTextState,
            onAddClick = {
                onAction(CreateChatAction.OnAddClick)
            },
            isSearchEnabled = canAddParticipant,
            isLoading = isSearching,
            error = searchError,
            onFocusChanged = {
                isTextFieldFocused = it
            }
        )
        ChirpHorizontalDivider()
        ChatParticipantsSelectionSection(
            modifier = Modifier.fillMaxWidth(),
            selectedParticipants = selectedChatParticipants,
            searchResult = currentSearchResult
        )
        ChirpHorizontalDivider()
        ManageChatButtonSection(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(top = 16.dp),
            primaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.create_chat),
                    onClick = {
                        onAction(CreateChatAction.OnCreateChatClick)
                    },
                    enabled = selectedChatParticipants.isNotEmpty(),
                    isLoading = isCreatingChat
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.cancel),
                    onClick = {
                        onAction(CreateChatAction.OnDismissDialog)
                    },
                    style = ChirpButtonStyle.SECONDARY
                )
            },
            errorMessage = createChatError?.asString()
        )
    }
}

@Preview
@Composable
fun PreviewCreateChatScreen() {
    ChirpTheme {
        CreateChatScreen(
            CreateChatState(
                selectedChatParticipants = listOf(
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum",
                        initials = "LI"
                    ),
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 2",
                        initials = "LI2"
                    )
                )
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
fun PreviewDarkCreateChatScreen() {
    ChirpTheme(darkTheme = true) {
        CreateChatScreen(
            CreateChatState(
                selectedChatParticipants = listOf(
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum",
                        initials = "LI"
                    ),
                    ChatParticipantUi(
                        id = Uuid.random().toString(),
                        username = "Lorem ipsum 2",
                        initials = "LI2"
                    )
                )
            ),
            onAction = {}
        )
    }
}

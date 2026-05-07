package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirpappkmp.core.designsystem.generated.resources.arrow_left_icon
import chirpappkmp.core.designsystem.generated.resources.dots_icon
import chirpappkmp.core.designsystem.generated.resources.log_out_icon
import chirpappkmp.core.designsystem.generated.resources.users_icon
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.chat_members
import chirpappkmp.feature.chat.presentation.generated.resources.go_back
import chirpappkmp.feature.chat.presentation.generated.resources.leave_chat
import chirpappkmp.feature.chat.presentation.generated.resources.open_chat_options_menu
import com.example.core.designsystem.components.buttons.ChirpIconButton
import com.example.core.designsystem.components.dropdown.ChirpDropDownMenu
import com.example.core.designsystem.components.dropdown.DropDownItem
import com.example.core.designsystem.theme.extended
import com.example.feature.chat.presentation.model.ChatUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import chirpappkmp.core.designsystem.generated.resources.Res as DesignSystemRes

@Composable
fun ChatDetailHeader(
    chatUi: ChatUi,
    isDetailPresent: Boolean,
    isGroupChat: Boolean,
    isChatOptionsDropDownOpen: Boolean,
    onChatOptionsClick: () -> Unit,
    onDismissChatOptions: () -> Unit,
    onManageChatClick: () -> Unit,
    onLeaveChatClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (!isDetailPresent) {
            ChirpIconButton(
                onClick = onBackClick
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = vectorResource(DesignSystemRes.drawable.arrow_left_icon),
                    contentDescription = stringResource(Res.string.go_back),
                    tint = MaterialTheme.colorScheme.extended.textSecondary
                )
            }
        }
        ChatItemHeaderRow(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onManageChatClick()
                },
            chatUi = chatUi,
            isGroupChat = isGroupChat,
        )

        Box {
            ChirpIconButton(
                onClick = onChatOptionsClick,
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = vectorResource(DesignSystemRes.drawable.dots_icon),
                    contentDescription = stringResource(Res.string.open_chat_options_menu),
                    tint = MaterialTheme.colorScheme.extended.textSecondary
                )
            }
            ChirpDropDownMenu(
                isOpen = isChatOptionsDropDownOpen,
                items = listOf(
                    DropDownItem(
                        title = stringResource(Res.string.chat_members),
                        icon = vectorResource(DesignSystemRes.drawable.users_icon),
                        contentColor = MaterialTheme.colorScheme.extended.textSecondary,
                        onClick = onManageChatClick
                    ),
                    DropDownItem(
                        title = stringResource(Res.string.leave_chat),
                        icon = vectorResource(DesignSystemRes.drawable.log_out_icon),
                        contentColor = MaterialTheme.colorScheme.extended.destructiveHover,
                        onClick = onLeaveChatClick
                    )
                ),
                onDismiss = {
                    onDismissChatOptions()
                },
            )
        }
    }
}
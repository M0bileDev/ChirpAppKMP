package com.example.feature.chat.presentation.chat_list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature.chat.domain.model.ChatParticipant

@Composable
fun ChatListHeader(
    localParticipant: ChatParticipant,
    isUserMenuOpen: Boolean,
    onUserAvatarClick: () -> Unit,
    onDismissMenu: () -> Unit,
    onProfileSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
}
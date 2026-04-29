package com.example.feature.chat.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature.chat.domain.model.ChatParticipant

@Composable
fun ProfileAvatarSection(
    localParticipant: ChatParticipant,
    isMenuOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onProfileSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) = with(localParticipant) {
    Box(
        modifier = modifier
    ) {}

}
package com.example.feature.chat.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.components.avatar.ChirpAvatarPhoto
import com.example.core.designsystem.theme.extended
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi

@Composable
fun ProfileAvatarSection(
    localParticipant: ChatParticipantUi,
    isMenuOpen: Boolean,
    onClick: () -> Unit,
    onDismissMenu: () -> Unit,
    onProfileSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) = with(localParticipant) {
    Box(
        modifier = modifier
    ) {
        ChirpAvatarPhoto(
            displayText = initials,
            imageUrl = imageUrl,
            onClick = onClick
        )
        //use above component as anchor
        DropdownMenu(
            expanded = isMenuOpen,
            onDismissRequest = onDismissMenu,
            containerColor = MaterialTheme.colorScheme.surface,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.extended.surfaceOutline
            )
        ) {
            // TODO: items 
        }
    }

}
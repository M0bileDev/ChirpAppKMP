@file:OptIn(ExperimentalUuidApi::class)

package com.example.feature.chat.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.components.brand.ChirpHorizontalDivider
import com.example.core.presentation.util.clearFocusOnTap
import com.example.feature.chat.presentation.profile.components.ProfileHeaderSection
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ProfileRoot(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) = with(state) {
    Column(
        modifier = Modifier
            .clearFocusOnTap()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        ProfileHeaderSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp,
                    horizontal = 20.dp
                ),
            username = username,
            onCloseClick = { onAction(ProfileAction.OnDismiss) }
        )
        ChirpHorizontalDivider()
    }
}





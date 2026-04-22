package com.example.feature.chat.presentation.components

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.presentation.util.UiText

@Composable
fun ChatParticipantSearchTextSection(
    queryState: TextFieldState,
    onSearchClick: () -> Unit,
    isSearchEnabled: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    error: UiText? = null,
    onFocusChanged: (Boolean) -> Unit
) {

}
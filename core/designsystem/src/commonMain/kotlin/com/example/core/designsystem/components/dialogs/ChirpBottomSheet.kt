@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.core.designsystem.components.dialogs

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    ModalBottomSheet(
        modifier = modifier.statusBarsPadding(),
        onDismissRequest = onDismiss,
        dragHandle = null,
        contentWindowInsets = { WindowInsets() }
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewChirpBottomSheet() {
}

@Preview
@Composable
fun PreviewDarkChirpBottomSheet() {
}
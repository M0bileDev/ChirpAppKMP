package com.example.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DestructiveConfirmationDialog(
    title: String,
    description: String,
    confirmationButtonText: String,
    cancelButtonText: String,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
fun PreviewDestructiveConfirmationDialog() {
    ChirpTheme {
        DestructiveConfirmationDialog()
    }
}

@Preview
@Composable
fun PreviewDarkDestructiveConfirmationDialog() {
    ChirpTheme(
        darkTheme = true
    ) {
        DestructiveConfirmationDialog()
    }
}
package com.example.feature.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.components.buttons.ChirpButton
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ManageChatButtonSection(
    primaryButton: @Composable () -> Unit,
    secondaryButton: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
    ) {
        primaryButton()
        secondaryButton()
    }
}

@Preview
@Composable
fun PreviewManageChatButtonSection() {
    ChirpTheme {
        ManageChatButtonSection(
            modifier = Modifier.fillMaxWidth(),
            primaryButton = {
                ChirpButton(text = "Lorem ipsum", onClick = {})
            },
            secondaryButton = {
                ChirpButton(text = "Lorem ipsum", onClick = {})
            }
        )
    }
}

@Preview
@Composable
fun PreviewDarkManageChatButtonSection() {
    ChirpTheme(
        darkTheme = true
    ) {
        ManageChatButtonSection(
            modifier = Modifier.fillMaxWidth(),
            primaryButton = {
                ChirpButton(text = "Lorem ipsum", onClick = {})
            },
            secondaryButton = {
                ChirpButton(text = "Lorem ipsum", onClick = {})
            }
        )
    }
}

package com.example.feature.chat.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.ChirpTheme
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.DeviceConfiguration
import com.example.feature.chat.presentation.type_alias.ChatParticipantUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ColumnScope.ChatParticipantsSelectionSection(
    selectedParticipants: List<ChatParticipantUi>,
    modifier: Modifier = Modifier,
    searchResult: ChatParticipantUi? = null
) {
    val deviceConfiguration = currentDeviceConfiguration()
    val rootHeightModifier = when (deviceConfiguration) {
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Modifier
                .animateContentSize()
                .heightIn(min = 200.dp, max = 300.dp)
        }

        else -> Modifier.weight(1f)
    }
}

@Preview
@Composable
fun PreviewChatParticipantsSelectionSection() {
    ChirpTheme {
        ChatParticipantsSelectionSection()
    }
}

@Preview
@Composable
fun PreviewDarkChatParticipantsSelectionSection() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatParticipantsSelectionSection()
    }
}

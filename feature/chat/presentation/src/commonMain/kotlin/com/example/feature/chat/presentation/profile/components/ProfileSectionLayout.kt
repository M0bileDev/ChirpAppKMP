package com.example.feature.chat.presentation.profile.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.DeviceConfiguration

@Composable
fun ProfileSectionLayout(
    headerText: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val deviceConfiguration = currentDeviceConfiguration()

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {}
        else -> {}
    }
}
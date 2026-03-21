package com.example.core.designsystem.components.layouts

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.extended
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.DeviceConfiguration

@Composable
fun ChirpAdaptiveFormLayout(
    headerText: String,
    logo: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    formContent: @Composable ColumnScope.() -> Unit,
) {
    val configuration = currentDeviceConfiguration()
    val headerColor = if (configuration == DeviceConfiguration.MOBILE_LANDSCAPE) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.extended.textPrimary
    }
}

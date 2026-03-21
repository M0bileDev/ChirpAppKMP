package com.example.core.designsystem.components.layouts

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChirpAdaptiveFormLayout(
    headerText: String,
    logo: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    formContent: @Composable ColumnScope.() -> Unit,
) {

}

package com.example.core.designsystem.components.layouts

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.components.brand.ChirpBrandLogo
import com.example.core.presentation.composableUtil.currentDeviceConfiguration
import com.example.core.presentation.util.DeviceConfiguration

@Composable
fun ChirpAdaptiveResultLayout(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val configuration = currentDeviceConfiguration()

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        if (configuration == DeviceConfiguration.MOBILE_PORTRAIT) {
            ChirpSurface(
                modifier = Modifier.padding(innerPadding),
                header = {
                    Spacer(modifier = Modifier.height(32.dp))
                    ChirpBrandLogo()
                    Spacer(modifier = Modifier.height(32.dp))
                },
                content = content
            )
        } else {
            //todo content for configuration other than MOBILE_PORTRAIT
        }
    }
}
package com.example.chirpappkmp

import androidx.compose.runtime.Composable
import com.example.core.designsystem.theme.ChirpTheme
import com.example.feature.auth.presentation.register.RegisterRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterRoot()
    }
}
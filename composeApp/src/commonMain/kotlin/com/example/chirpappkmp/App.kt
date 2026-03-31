package com.example.chirpappkmp

import androidx.compose.runtime.Composable
import com.example.chirpappkmp.navigation.NavigationRoot
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        NavigationRoot()
    }
}
package com.example.chirpappkmp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.chirpappkmp.navigation.DeepLinkListener
import com.example.chirpappkmp.navigation.NavigationRoot
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    DeepLinkListener(navController)
    ChirpTheme {
        NavigationRoot(navController)
    }
}
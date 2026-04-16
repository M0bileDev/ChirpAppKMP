package com.example.core.designsystem.components.avatar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpStackedAvatars(modifier: Modifier = Modifier){

}

@Preview
@Composable
fun PreviewChirpStackedAvatars(){
    ChirpTheme {
        ChirpStackedAvatars()
    }
}

@Preview
@Composable
fun PreviewDarkChirpStackedAvatars(){
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpStackedAvatars()
    }
}
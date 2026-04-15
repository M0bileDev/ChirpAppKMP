package com.example.core.designsystem.components.avatar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAvatarPhoto(modifier: Modifier = Modifier){

}

@Preview
@Composable
fun PreviewChirpAvatarPhoto(){
    ChirpTheme {
        ChirpAvatarPhoto()
    }
}

@Preview
@Composable
fun PreviewDarkChirpAvatarPhoto(){
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpAvatarPhoto()
    }
}
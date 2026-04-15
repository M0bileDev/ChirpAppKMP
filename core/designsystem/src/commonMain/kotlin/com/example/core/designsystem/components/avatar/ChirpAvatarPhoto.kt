package com.example.core.designsystem.components.avatar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class AvatarSize(val dp: Dp){
    REGULAR(40.dp),
    LARGE(60.dp)
}

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
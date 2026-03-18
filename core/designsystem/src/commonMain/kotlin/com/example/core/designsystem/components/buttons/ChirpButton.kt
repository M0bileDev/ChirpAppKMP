package com.example.core.designsystem.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ChirpButtonStyle {
    PRIMARY,
    DESTRUCTIVE_PRIMARY,
    SECONDARY,
    DESTRUCTIVE_SECONDARY,
    TEXT
}

@Composable
fun ChirpButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(15.dp).alpha(
                    alpha = if (isLoading) 1f else 0f
                ),
                strokeWidth = 1.5.dp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(alpha = if (isLoading) 0f else 1f)
            ) {
                leadingIcon?.invoke()
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPrimaryChirpButton() {
    ChirpTheme {
        ChirpButton(
            text = "Lorem ipsum",
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewDestructivePrimaryChirpButton() {
    ChirpTheme {
        ChirpButton(
            text = "Lorem ipsum",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_PRIMARY
        )
    }
}

@Preview
@Composable
fun PreviewSecondaryChirpButton() {
    ChirpTheme {
        ChirpButton(
            text = "Lorem ipsum",
            onClick = {},
            style = ChirpButtonStyle.SECONDARY
        )
    }
}

@Preview
@Composable
fun PreviewDestructiveSecondaryChirpButton() {
    ChirpTheme {
        ChirpButton(
            text = "Lorem ipsum",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_SECONDARY
        )
    }
}

@Preview
@Composable
fun PreviewTextChirpButton() {
    ChirpTheme {
        ChirpButton(
            text = "Lorem ipsum",
            onClick = {},
            style = ChirpButtonStyle.TEXT
        )
    }
}
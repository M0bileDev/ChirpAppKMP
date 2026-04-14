package com.example.core.designsystem.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.extended

@Composable
fun ChirpChatBubble(
    messageContent: String,
    sender: String,
    formattedDateTime: String,
    trianglePosition: TrianglePosition,
    modifier: Modifier = Modifier,
    contentPaddingSize: Dp = 12.dp,
    color: Color = MaterialTheme.colorScheme.extended.surfaceHigher,
    messageStatus: @Composable (() -> Unit)? = null,
    triangleSize: Dp = 16.dp,
    onLongClick: (() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .then(
                if (onLongClick != null) Modifier.combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(
                        color = MaterialTheme.colorScheme.extended.surfaceOutline
                    ),
                    onLongClick = onLongClick,
                    onClick = {}
                ) else Modifier
            ).clip(
                ChatBubbleShape(
                    trianglePosition = trianglePosition,
                    triangleSize = triangleSize
                )
            ).background(color)
            .padding(
                start = if (trianglePosition == TrianglePosition.LEFT) {
                    contentPaddingSize + triangleSize
                } else contentPaddingSize,
                end = if (trianglePosition == TrianglePosition.RIGHT) {
                    contentPaddingSize + triangleSize
                } else contentPaddingSize,
                top = contentPaddingSize,
                bottom = contentPaddingSize
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = sender,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.extended.textSecondary
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = formattedDateTime,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.extended.textSecondary
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = messageContent,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            textAlign = TextAlign.Justify
        )
        messageStatus?.let { status ->
            Spacer(modifier = Modifier.height(16.dp))
            status()
        }
    }
}






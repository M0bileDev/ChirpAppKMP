package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun DateChip(
    date: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(color = MaterialTheme.colorScheme.surface)
    ) {

    }
}
package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature.chat.presentation.model.ChatUi

@Composable
fun ChatDetailHeader(
    chatUi: ChatUi,
    isDetailPresent: Boolean,
    isChatOptionsDropDownOpen: Boolean,
    onChatOptionsClick: () -> Unit,
    onDismissChatOptions: () -> Unit,
    onManageChatClick: () -> Unit,
    onLeaveChatClick: () -> Unit,
    modifier: Modifier = Modifier
) {

}
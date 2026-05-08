package com.example.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import chirpappkmp.feature.chat.presentation.generated.resources.Res
import chirpappkmp.feature.chat.presentation.generated.resources.send_a_message
import com.example.core.designsystem.components.textfields.ChirpMultiLineTextField
import com.example.feature.chat.domain.model.ConnectionState
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageBox(
    messageTextFieldState: TextFieldState,
    isTextInputEnabled: Boolean,
    connectionState: ConnectionState,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ChirpMultiLineTextField(
        modifier = modifier.padding(4.dp),
        state = messageTextFieldState,
        placeholder = stringResource(Res.string.send_a_message),
        enabled = isTextInputEnabled,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Send
        ),
        onKeyboardAction = onSendClick
    )
}
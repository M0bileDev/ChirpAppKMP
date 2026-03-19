package com.example.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.ChirpTheme
import com.example.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    singleLine: Boolean = false,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onFocusChanged: (Boolean) -> Unit = {},
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onFocusChanged(isFocused)
    }

    Column(
        modifier = modifier
    ) {
        ChirpTextFieldTitle(title)
        ChirpTextFieldBody(
            isFocused,
            enabled,
            isError,
            state,
            singleLine,
            keyboardType,
            interactionSource,
            placeholder
        )
        ChirpTextFieldSupportingText(supportingText, isError)
    }
}

@Composable
private fun ChirpTextFieldTitle(title: String?) {
    if (title != null) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.extended.textSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ChirpTextFieldBody(
    isFocused: Boolean,
    enabled: Boolean,
    isError: Boolean,
    state: TextFieldState,
    singleLine: Boolean,
    keyboardType: KeyboardType,
    interactionSource: MutableInteractionSource,
    placeholder: String?
) {
    BasicTextField(
        modifier = Modifier.fillMaxWidth().background(
            color = when {
                isFocused -> MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.05f
                )

                enabled -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.extended.secondaryFill
            },
            shape = RoundedCornerShape(8.dp)
        ).border(
            width = 1.dp,
            color = when {
                isError -> MaterialTheme.colorScheme.error
                isFocused -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outline
            },
            shape = RoundedCornerShape(8.dp)
        )
            .padding(12.dp),
        state = state,
        enabled = enabled,
        lineLimits = if (singleLine) {
            TextFieldLineLimits.SingleLine
        } else {
            TextFieldLineLimits.Default
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = if (enabled) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.extended.textPlaceholder
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        cursorBrush = SolidColor(value = MaterialTheme.colorScheme.onSurface),
        interactionSource = interactionSource,
        decorator = { innerBox ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (state.text.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.extended.textPlaceholder,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            innerBox()
        }
    )
}

@Composable
private fun ChirpTextFieldSupportingText(supportingText: String?, isError: Boolean) {
    if (supportingText != null) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = supportingText,
            color = if (isError) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.extended.textTertiary
            },
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEmptyChirpTextField() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewFilledChirpTextField() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(
                initialText = "Lorem ipsum initial text"
            ),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDisabledChirpTextField() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
            enabled = false
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewErrorChirpTextField() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
            isError = true
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun PreviewEmptyDarkChirpTextField() {
    ChirpTheme(darkTheme = true) {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun PreviewFilledDarkChirpTextField() {
    ChirpTheme(darkTheme = true) {
        ChirpTextField(
            state = rememberTextFieldState(
                initialText = "Lorem ipsum initial text"
            ),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun PreviewDisabledDarkChirpTextField() {
    ChirpTheme(darkTheme = true) {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
            enabled = false
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun PreviewErrorDarkChirpTextField() {
    ChirpTheme(darkTheme = true) {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Lorem ipsum placeholder",
            title = "Lorem ipsum title",
            supportingText = "Lorem ipsum supporting text",
            isError = true
        )
    }
}
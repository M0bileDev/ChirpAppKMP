package com.example.core.presentation.util

import org.jetbrains.compose.resources.StringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class Resource(
        val id: StringResource,
        val args: Array<Any> = arrayOf()
    ) : UiText
}
package com.example.feature.chat.domain.error

import com.example.core.domain.util.Error

enum class ConnectionError : Error{
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED
}
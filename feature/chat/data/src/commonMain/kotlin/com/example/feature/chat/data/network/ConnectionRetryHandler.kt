package com.example.feature.chat.data.network

class ConnectionRetryHandler(
    private val connectionErrorHandler: ConnectionErrorHandler
) {
    private var shouldSkipBackoff = false

    fun shouldRetry(cause: Throwable): Boolean {
        return connectionErrorHandler.isRetriableError(cause)
    }
}
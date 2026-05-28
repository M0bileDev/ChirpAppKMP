package com.example.feature.chat.data.network

import kotlin.math.pow

class ConnectionRetryHandler(
    private val connectionErrorHandler: ConnectionErrorHandler
) {
    private var shouldSkipBackoff = false

    fun shouldRetry(cause: Throwable): Boolean {
        return connectionErrorHandler.isRetriableError(cause)
    }

    private fun createBackoffDelay(attempt: Int): Long {
        // 2 to power of attempt * 2 seconds
        val delayTime = 2f.pow(attempt).toLong() * 2_000L
        // 30 seconds
        val maxDelay = 30_000L
        return minOf(delayTime, maxDelay)
    }
}
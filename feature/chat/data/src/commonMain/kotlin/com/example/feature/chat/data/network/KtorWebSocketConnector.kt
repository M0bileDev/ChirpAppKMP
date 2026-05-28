package com.example.feature.chat.data.network

import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.logging.ChirpLogger
import com.example.feature.chat.data.lifecycle.AppLifecycleObserver
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

class KtorWebSocketConnector(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val connectionErrorHandler: ConnectionErrorHandler,
    private val connectionRetryHandler: ConnectionRetryHandler,
    private val appLifecycleObserver: AppLifecycleObserver,
    private val connectivityObserver: ConnectivityObserver,
    private val logger: ChirpLogger
) {
}
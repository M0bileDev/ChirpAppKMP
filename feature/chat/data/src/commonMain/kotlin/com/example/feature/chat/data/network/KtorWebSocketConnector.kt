@file:OptIn(FlowPreview::class)

package com.example.feature.chat.data.network

import com.example.core.data.network.UrlConstants.BASE_URL_WS
import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.logging.ChirpLogger
import com.example.feature.chat.data.BuildKonfig
import com.example.feature.chat.data.dto.websocket.WebSocketMessageDto
import com.example.feature.chat.data.lifecycle.AppLifecycleObserver
import com.example.feature.chat.domain.model.ConnectionState
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

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
    private var webSocketSession: WebSocketSession? = null
    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState = _connectionState.asStateFlow()

    private val isConnected = connectivityObserver
        .isConnected
        .debounce(1.seconds)
        .stateIn(
            applicationScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            false
        )

    private val isInForeground = appLifecycleObserver
        .isInForeground
        .onEach { isInForeground ->
            if (isInForeground) {
                connectionRetryHandler.resetDelay()
            }
        }
        .stateIn(
            applicationScope,
            SharingStarted.WhileSubscribed(5_000L),
            false
        )

    private fun createWebSocketFlow(accessToken: String) = callbackFlow {
        _connectionState.value = ConnectionState.CONNECTING

        webSocketSession = httpClient.webSocketSession(
            urlString = "$BASE_URL_WS/chat"
        ) {

            header("Authorization", "Bearer $accessToken")
            header("X-API-Key", BuildKonfig.API_KEY)
        }

        webSocketSession?.let { session ->
            _connectionState.value = ConnectionState.CONNECTED

            session
                .incoming
                .consumeAsFlow()
                .buffer(capacity = 100)
                .collect { frame ->
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            logger.info("Received raw text frame: $text")

                            val messageDto = json.decodeFromString<WebSocketMessageDto>(text)
                            send(messageDto)
                        }

                        is Frame.Ping -> {
                            logger.debug("Received ping from server, sending pong...")
                            session.send(Frame.Pong(frame.data))
                        }

                        else -> Unit
                    }
                }
        }
    }
}
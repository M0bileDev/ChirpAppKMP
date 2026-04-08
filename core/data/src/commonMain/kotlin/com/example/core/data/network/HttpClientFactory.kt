package com.example.core.data.network

import com.example.core.data.BuildKonfig
import com.example.core.domain.auth.SessionStorage
import com.example.core.domain.logging.ChirpLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val chirpLogger: ChirpLogger,
    private val sessionStorage: SessionStorage
) {

    companion object {
        const val DEFAULT_TIMEOUT = 20_000L
    }

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                //websocket connection
                socketTimeoutMillis = DEFAULT_TIMEOUT
                //ongoing http request
                requestTimeoutMillis = DEFAULT_TIMEOUT
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        chirpLogger.debug(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(WebSockets) {
                pingIntervalMillis = DEFAULT_TIMEOUT
            }
            //attach headers to each request
            defaultRequest {
                header("x-api-key", BuildKonfig.API_KEY)
                contentType(ContentType.Application.Json)
            }
            install(Auth) {
                bearer {
                    //tokens load from local storage
                    loadTokens {
                        sessionStorage
                            .observeAuthInfo()
                            .firstOrNull()
                            ?.let { authInfo ->
                                BearerTokens(
                                    refreshToken = authInfo.refreshToken,
                                    accessToken = authInfo.accessToken
                                )
                            }
                    }
                }
            }
        }
    }
}
package com.example.feature.chat.data.notification

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.feature.chat.domain.notification.DeviceTokenService
import io.ktor.client.HttpClient

class KtorDeviceTokenService(
    private val httpClient: HttpClient
) : DeviceTokenService{

    override suspend fun registerToken(
        token: String,
        platform: String
    ): EmptyResult<DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun unregisterToken(token: String): EmptyResult<DataError.Remote> {
        TODO("Not yet implemented")
    }
}
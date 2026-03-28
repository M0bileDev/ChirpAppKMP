package com.example.core.data.auth

import com.example.core.data.dto.register.RegisterRequest
import com.example.core.data.network.post
import com.example.core.domain.auth.AuthService
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient
) : AuthService {

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(email, username, password)
        )
    }
}
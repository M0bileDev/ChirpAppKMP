package com.example.core.domain.auth

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote>
}
package com.example.core.data.mappers

import com.example.core.data.dto.AuthInfoSerializable
import com.example.core.data.dto.UserSerializable
import com.example.core.domain.auth.AuthInfo
import com.example.core.domain.auth.User

fun AuthInfoSerializable.toDomain(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toDomain()
    )
}

fun UserSerializable.toDomain(): User {
    return User(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl
    )
}
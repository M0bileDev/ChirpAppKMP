package com.example.feature.chat.data.notification

import com.example.core.domain.logging.ChirpLogger
import com.example.feature.chat.domain.notification.PushNotificationTokenService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class FirebasePushNotificationService(
    private val logger: ChirpLogger
) : PushNotificationTokenService {
    actual override fun observeDeviceToken(): Flow<String?> = flow {
        // Firebase/FCM is not wired on iOS yet; APNs token handling can be added here.
        logger.info("FCM token retrieval is not implemented on iOS")
        emit(null)
    }
}

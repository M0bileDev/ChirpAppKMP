package com.example.feature.chat.data.network

import com.example.feature.chat.domain.model.ConnectionState
import platform.Foundation.NSError
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorNetworkConnectionLost
import platform.Foundation.NSURLErrorNotConnectedToInternet

actual class ConnectionErrorHandler {
    actual fun getConnectionStateFromError(cause: Throwable): ConnectionState {
        TODO("Not yet implemented")
    }

    actual fun transformException(exception: Throwable): Throwable {
        TODO("Not yet implemented")
    }

    actual fun isRetriableError(cause: Throwable): Boolean {
        TODO("Not yet implemented")
    }

    private fun extractNsError(cause: Throwable): NSError? {
        val throwableCause = cause.cause

        if (throwableCause is NSError) {
            return throwableCause
        }

        if (cause is NSError) {
            return cause
        }

        val exceptionNSError = cause.toNSError()
        val causeNSError = cause.cause?.toNSError()

        return exceptionNSError ?: causeNSError
    }

    private fun Throwable.toNSError(): NSError? {
        return message?.let { message ->
            when {
                message.contains(NSURLErrorNotConnectedToInternetPattern) -> {
                    return NSError.errorWithDomain(
                        domain = NSURLErrorDomain,
                        code = NSURLErrorNotConnectedToInternet,
                        userInfo = null
                    )
                }

                message.contains(NSURLErrorNotConnectionLostPattern) -> {
                    return NSError.errorWithDomain(
                        domain = NSURLErrorDomain,
                        code = NSURLErrorNetworkConnectionLost,
                        userInfo = null
                    )
                }

                else -> null
            }
        }
    }

    companion object {
        private val NSURLErrorNotConnectedToInternetPattern =
            "Error Domain=${NSURLErrorDomain} Code=${NSURLErrorNotConnectedToInternet}"

        private val NSURLErrorNotConnectionLostPattern =
            "Error Domain=${NSURLErrorDomain} Code=${NSURLErrorNetworkConnectionLost}"
    }
}
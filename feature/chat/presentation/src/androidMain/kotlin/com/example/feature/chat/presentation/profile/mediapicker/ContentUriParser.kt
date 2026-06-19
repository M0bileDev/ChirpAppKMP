package com.example.feature.chat.presentation.profile.mediapicker

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentUriParser(
    private val context: Context
) {
    suspend fun readUri(uri: Uri): ByteArray? {
        return withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }
        }
    }
}
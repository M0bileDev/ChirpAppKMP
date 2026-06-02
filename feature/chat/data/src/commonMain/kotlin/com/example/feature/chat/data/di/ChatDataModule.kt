package com.example.feature.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.feature.chat.data.chat.KtorChatParticipantService
import com.example.feature.chat.data.chat.KtorChatService
import com.example.feature.chat.data.chat.OfflineFirstChatRepository
import com.example.feature.chat.data.chat.WebSocketChatConnectionClient
import com.example.feature.chat.data.message.KtorChatMessageService
import com.example.feature.chat.data.message.OfflineFirstMessageRepository
import com.example.feature.chat.data.network.KtorWebSocketConnector
import com.example.feature.chat.database.ChirpDatabaseFactory
import com.example.feature.chat.domain.chat.ChatConnectionClient
import com.example.feature.chat.domain.chat.ChatParticipantService
import com.example.feature.chat.domain.chat.ChatRepository
import com.example.feature.chat.domain.chat.ChatService
import com.example.feature.chat.domain.message.ChatMessageService
import com.example.feature.chat.domain.message.MessageRepository
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module

val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    singleOf(::OfflineFirstChatRepository) bind ChatRepository::class
    singleOf(::OfflineFirstMessageRepository) bind MessageRepository::class
    singleOf(::WebSocketChatConnectionClient) bind ChatConnectionClient::class
    singleOf(::KtorChatMessageService) bind ChatMessageService::class
    singleOf(::KtorWebSocketConnector)
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
    single {
        get<ChirpDatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}
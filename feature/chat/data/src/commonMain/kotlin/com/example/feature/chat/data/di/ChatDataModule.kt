package com.example.feature.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.feature.chat.data.chat.KtorChatParticipantService
import com.example.feature.chat.data.chat.KtorChatService
import com.example.feature.chat.database.ChirpDatabaseFactory
import com.example.feature.chat.domain.chat.ChatParticipantService
import com.example.feature.chat.domain.chat.ChatService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module

val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    single {
        get<ChirpDatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}
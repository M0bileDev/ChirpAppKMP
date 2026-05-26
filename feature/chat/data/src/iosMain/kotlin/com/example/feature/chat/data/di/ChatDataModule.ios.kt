package com.example.feature.chat.data.di

import com.example.feature.chat.data.lifecycle.AppLifecycleObserver
import com.example.feature.chat.database.ChirpDatabaseFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule: Module = module {
    single { ChirpDatabaseFactory() }
    singleOf(::AppLifecycleObserver)
}
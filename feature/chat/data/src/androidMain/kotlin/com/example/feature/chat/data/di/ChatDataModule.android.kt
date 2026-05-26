package com.example.feature.chat.data.di

import com.example.feature.chat.data.lifecycle.AppLifecycleObserver
import com.example.feature.chat.data.network.ConnectivityObserver
import com.example.feature.chat.database.ChirpDatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule: Module = module {
    single { ChirpDatabaseFactory(androidContext()) }
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
}
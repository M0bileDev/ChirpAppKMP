package com.example.feature.chat.data.di

import com.example.feature.chat.data.chat.KtorChatParticipantService
import com.example.feature.chat.domain.chat.ChatParticipantService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
}
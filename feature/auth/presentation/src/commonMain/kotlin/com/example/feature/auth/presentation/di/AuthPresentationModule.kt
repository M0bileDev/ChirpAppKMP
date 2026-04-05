package com.example.feature.auth.presentation.di

import com.example.feature.auth.presentation.email_verification.EmailVerificationViewModel
import com.example.feature.auth.presentation.login.LoginViewModel
import com.example.feature.auth.presentation.register.RegisterViewModel
import com.example.feature.auth.presentation.register_success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
}
package com.example.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onAction(registerAction: RegisterAction){
        when(registerAction){
            else -> TODO("handle actions")
        }
    }
}
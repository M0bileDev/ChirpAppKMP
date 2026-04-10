package com.example.feature.auth.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ForgotPasswordViewModel : ViewModel() {
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // TODO: implement load logic
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ForgotPasswordState()
        )


    fun onAction(forgotPasswordAction: ForgotPasswordAction) {}
}
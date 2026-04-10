package com.example.chirpappkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private var previousRefreshToken: String? = null
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val authInfo = sessionStorage.observeAuthInfo().firstOrNull()
            _state.update {
                it.copy(
                    isCheckingAuth = false,
                    isLoggedIn = authInfo != null
                )
            }
        }
    }

    private fun observeSession(){
        sessionStorage
            .observeAuthInfo()
            .onEach { authInfo ->
                val currentRefreshToken = authInfo?.refreshToken
                //user has been logged in, current refresh token is not null, during application
                //lifecycle user made action that made token expired ->  currentRefreshToken = null
                val isSessionExpired = previousRefreshToken != null && currentRefreshToken == null

                if(isSessionExpired){
                    sessionStorage.set(null)
                    _state.update { it.copy(
                        isLoggedIn = false
                    ) }
                }

                previousRefreshToken = currentRefreshToken
            }
            .launchIn(viewModelScope)
    }
}
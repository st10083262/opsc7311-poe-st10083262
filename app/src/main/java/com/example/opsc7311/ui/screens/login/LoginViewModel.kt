package com.example.opsc7311.ui.screens.login

import androidx.lifecycle.ViewModel
import com.example.opsc7311.viewmodels.SharedViewModelUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    // UI State
    private val _uiState = MutableStateFlow(LoginViewModelUiState())
    val uiState: StateFlow<LoginViewModelUiState> = _uiState.asStateFlow()

    fun setUsername(text: String)
    {
        _uiState.update { currentState ->
            currentState.copy(username = text)
        }
    }

    fun setPassword(text: String)
    {
        _uiState.update { currentState ->
            currentState.copy(password = text)
        }
    }

    fun setError()
    {
        _uiState.update { currentState ->
            currentState.copy(isError = true)
        }
    }

    fun onSignInResult(result: SignInResult)
    {
        _uiState.update { currentState ->
            currentState.copy(
                isLoggedIn = result.data != null,
                signInErrorMessage = result.errorMessage
            )
        }
    }

    fun resetState() {
        _uiState.update { LoginViewModelUiState()}
    }
}
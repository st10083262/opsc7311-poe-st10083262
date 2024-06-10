package com.example.opsc7311.ui.screens.login

data class LoginViewModelUiState(
    val username: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val isLoggedIn: Boolean = false,
    val signInErrorMessage: String? = null
)

package com.example.opsc7311.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.opsc7311.ui.screens.home.HomeScreen
import com.example.opsc7311.ui.screens.login.LoginScreen
import com.example.opsc7311.ui.screens.login.LoginViewModel
import com.example.opsc7311.ui.screens.login.LoginViewModelUiState
import com.example.opsc7311.viewmodels.SharedViewModel

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "authentication_graph"
    const val HOME = "home_graph"
    const val TIMESHEETS = "timesheet_graph"
}

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    onLoginButtonPressed: () -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        // Do not pass navController as it has its own NavHost.
        // Each NavHost must have its own navHostController
        composable(route = Graph.HOME) {
            HomeScreen()
        }

        composable(route = Graph.AUTHENTICATION) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onSuccessfulLogin = onLoginButtonPressed
            )
        }
    }
}
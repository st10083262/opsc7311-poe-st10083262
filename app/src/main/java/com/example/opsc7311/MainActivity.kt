package com.example.opsc7311

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Identity
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.opsc7311.ui.navigation.Graph
import com.example.opsc7311.ui.navigation.RootNavigationGraph
import com.example.opsc7311.ui.screens.login.AuthUiClient
import com.example.opsc7311.ui.screens.login.LoginViewModel
import com.example.opsc7311.ui.theme.Opsc7311Theme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        AuthUiClient(
            context = applicationContext,
            oneTapClient = com.google.android.gms.auth.api.identity.Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Opsc7311Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val navController = rememberNavController()

                    val loginViewModel: LoginViewModel = viewModel()
                    val loginState by loginViewModel.uiState.collectAsState()

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if(result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.SignInWithIntent(
                                        intent = result.data?: return@launch
                                    )
                                    loginViewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )

                    LaunchedEffect(key1 = loginState.isLoggedIn) {
                        if(loginState.isLoggedIn) {
                            Toast.makeText(applicationContext, "Sign In Successful", Toast
                                .LENGTH_LONG).show()

                            navController.navigate(route = Graph.HOME){
                                popUpTo(route = Graph.AUTHENTICATION) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    RootNavigationGraph(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        onLoginButtonPressed = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUiClient.SignIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

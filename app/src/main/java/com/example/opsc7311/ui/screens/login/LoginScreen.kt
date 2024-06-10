package com.example.opsc7311.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onSuccessfulLogin: () -> Unit,

) {
    val loginViewModelUiState by loginViewModel.uiState.collectAsState()
    LoginScreenContent(
        loginViewModelUiState = loginViewModelUiState,
        onLoginButtonPressed = onSuccessfulLogin,
        onUsernameTextChanged = {
            loginViewModel.setUsername(it)
        },
        onPasswordTextChanged = {
            loginViewModel.setPassword(it)
        }
    )
}

@Composable
fun LoginScreenContent(
    loginViewModelUiState: LoginViewModelUiState,
    onLoginButtonPressed: () -> Unit,
    onUsernameTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
) {
    
    val context = LocalContext.current
    LaunchedEffect(key1 = loginViewModelUiState.signInErrorMessage) {
        loginViewModelUiState.signInErrorMessage?.let {error->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login", style = TextStyle(fontSize = 40.sp))

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Username") },
                    value = loginViewModelUiState.username,
                    onValueChange = onUsernameTextChanged
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Password") },
                    value = loginViewModelUiState.password,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = onPasswordTextChanged
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = onLoginButtonPressed,
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Login")
                    }
                }
                if(loginViewModelUiState.isError){
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Your username or password is " +
                            "incorrect!" + loginViewModelUiState.signInErrorMessage,
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        loginViewModelUiState = LoginViewModelUiState(),
        onLoginButtonPressed = { /*TODO*/ },
        onUsernameTextChanged = {},
        onPasswordTextChanged = {}
    )
}
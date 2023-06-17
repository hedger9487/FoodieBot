package com.foodiebot.app.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.foodiebot.app.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onLoginFailure: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") }
        )
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") }
        )
        Button(
            onClick = {
                viewModel.loginUser(
                    email = emailState.value,
                    password = passwordState.value,
                    onSuccess = onLoginSuccess,
                    onFailure = onLoginFailure
                )
            }
        ) {
            Text("Login")
        }
        Button(
            onClick = onRegisterClick
        ) {
            Text("Register")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(LoginViewModel(), {}, {}, {})
}

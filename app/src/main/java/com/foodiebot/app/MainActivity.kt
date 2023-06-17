package com.foodiebot.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.foodiebot.app.ui.theme.FoodieBotTheme
import com.foodiebot.app.ui.theme.LoginScreen
import com.foodiebot.app.ui.theme.RegistrationScreen
import com.foodiebot.app.viewmodel.LoginViewModel
import com.foodiebot.app.viewmodel.RegistrationViewModel


//import android.content.Intent
//import com.foodiebot.app.login.LoginActivity
class MainActivity : ComponentActivity() {
    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var loginViewModel: LoginViewModel

    private var isRegisterScreenVisible by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setContent {
            FoodieBotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isRegisterScreenVisible) {
                        RegistrationScreen(
                            viewModel = registrationViewModel,
                            onRegistrationSuccess = {
                                // Handle registration success
                                Toast.makeText(this@MainActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                                // Set the register screen visibility to false
                                isRegisterScreenVisible = false
                            },
                            onRegistrationFailure = { error ->
                                // Handle registration failure
                                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        LoginScreen(
                            viewModel = loginViewModel,
                            onLoginSuccess = {
                                // Handle login success
                                Toast.makeText(this@MainActivity, "Login successful", Toast.LENGTH_SHORT).show()
                                // Navigate to another screen
                            },
                            onLoginFailure = { error ->
                                // Handle login failure
                                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                            },
                            onRegisterClick = {
                                // Set the register screen visibility to true
                                isRegisterScreenVisible = true
                            }
                        )
                    }
                }
            }
        }
    }
}

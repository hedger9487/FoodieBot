package com.foodiebot.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.foodiebot.app.ui.theme.FoodieBotTheme
import com.foodiebot.app.ui.theme.RegistrationScreen
import com.foodiebot.app.viewmodel.RegistrationViewModel
//import android.content.Intent
//import com.foodiebot.app.login.LoginActivity



class MainActivity : ComponentActivity() {
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        setContent {
            FoodieBotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistrationScreen(
                        viewModel = registrationViewModel,
                        onRegistrationSuccess = {
                            // Display a success message
                            Toast.makeText(this@MainActivity, "Registration successful", Toast.LENGTH_SHORT).show()

                            // Start the login activity
       //                     val intent = Intent(this@MainActivity, LoginActivity::class.java)
         //                   startActivity(intent)
           //                 finish() // Optional: Close the current activity if needed
                        },
                        onRegistrationFailure = { error ->
                            // Registration failed, show error message
                            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

// Other code remains the same...

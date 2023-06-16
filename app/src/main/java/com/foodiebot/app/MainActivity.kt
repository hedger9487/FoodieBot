package com.foodiebot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.foodiebot.app.ui.theme.FoodieBotTheme
import com.foodiebot.app.viewmodel.RegistrationViewModel
import com.foodiebot.app.ui.theme.RegistrationScreen

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
                    RegistrationScreen(viewModel = registrationViewModel)
                }
            }
        }
    }
}

// Other code remains the same...

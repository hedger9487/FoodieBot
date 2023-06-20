package com.foodiebot.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : ComponentActivity() {
    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private var isRegisterScreenVisible by mutableStateOf(false)

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Handle the result here
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInClient = buildGoogleSignInClient()

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
                                val signInIntent = googleSignInClient.signInIntent
                                signInLauncher.launch(signInIntent)
                            },
                            onGoogleSignIn = {
                                // Initiate the Google Sign-In process
                                val signInIntent = googleSignInClient.signInIntent
                                signInLauncher.launch(signInIntent)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun buildGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                // Handle sign-in failure
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success
                    val user = firebaseAuth.currentUser
                    // Handle successful sign-in
                    // ...
                } else {
                    // Sign-in failed
                    Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}

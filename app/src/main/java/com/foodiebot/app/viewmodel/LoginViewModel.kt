package com.foodiebot.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        // Validate user input
        if (email.isBlank() || password.isBlank()) {
            onFailure("Please enter email and password")
            return
        }

        // Log in the user with email and password
        viewModelScope.launch {
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                if (authResult.user != null) {
                    onSuccess()
                } else {
                    onFailure("Login failed")
                }
            } catch (e: Exception) {
                onFailure(e.localizedMessage ?: "Login failed")
            }
        }
    }
}

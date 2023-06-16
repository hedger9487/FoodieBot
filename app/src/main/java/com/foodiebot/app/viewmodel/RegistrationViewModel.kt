package com.foodiebot.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(name: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        // Validate user input
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            onFailure("Please fill in all fields")
            return
        }

        // Perform additional validation (e.g., check password length and symbols)

        // Create a new user with email and password
        viewModelScope.launch {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                if (authResult.user != null) {
                    onSuccess()
                } else {
                    onFailure("Registration failed")
                }
            } catch (e: Exception) {
                onFailure(e.localizedMessage ?: "Registration failed")
            }
        }
    }
}

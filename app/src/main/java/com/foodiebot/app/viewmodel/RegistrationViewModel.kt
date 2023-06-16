package com.foodiebot.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // Validate user input
        if (!isNameValid(name)) {
            onFailure("Invalid name. Name should be between 2 and 12 characters.")
            return
        }

        if (!isEmailValid(email)) {
            onFailure("Invalid email format.")
            return
        }

        if (!isPasswordValid(password)) {
            onFailure("Invalid password. Password should be between 6 and 18 characters.")
            return
        }

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

    private fun isNameValid(name: String): Boolean {
        return name.length in 2..12
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return email.matches(emailRegex)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length in 6..18
    }
}

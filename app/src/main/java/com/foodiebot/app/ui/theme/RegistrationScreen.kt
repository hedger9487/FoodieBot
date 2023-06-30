package com.foodiebot.app.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.foodiebot.app.R
import com.foodiebot.app.viewmodel.FoodPreferenceViewModel
import com.foodiebot.app.viewmodel.RegistrationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onRegistrationSuccess: (String) -> Unit,
    onRegistrationFailure: (String) -> Unit,
    foodPreferenceViewModel: FoodPreferenceViewModel
) {
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(
                value = nameState.value,
                onValueChange = { nameState.value = it.trim() },
                label = { Text(text = stringResource(R.string.label_name)) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = emailState.value,
                onValueChange = { emailState.value = it.trim() },
                label = { Text(text = stringResource(R.string.label_email)) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it.trim() },
                label = { Text(text = stringResource(R.string.label_password)) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text("Food Preferences")
            FoodPreferenceCheckBox("Hotpot", foodPreferenceViewModel)
            FoodPreferenceCheckBox("Chinese Food", foodPreferenceViewModel)
            FoodPreferenceCheckBox("Fast Food", foodPreferenceViewModel)
            FoodPreferenceCheckBox("Healthy Food", foodPreferenceViewModel)
        }

        item {
            Button(
                onClick = {
                    viewModel.registerUser(
                        name = nameState.value,
                        email = emailState.value,
                        password = passwordState.value,
                        onSuccess = { userId -> // Receive the userId
                            // Save food preferences after user registration
                            foodPreferenceViewModel.saveFoodPreferences(userId)
                            onRegistrationSuccess(userId) // Pass the userId to the callback
                        },
                        onFailure = { error ->
                            onRegistrationFailure(error)
                        }
                    )
                },
                modifier = Modifier.padding(top = 16.dp) // Add padding to separate button from checkboxes
            ) {
                Text(text = stringResource(R.string.btn_register))
            }
        }
    }
}

@Composable
fun FoodPreferenceCheckBox(food: String, viewModel: FoodPreferenceViewModel) {
    val isChecked = viewModel.foodPreferences.contains(food)

    Checkbox(
        checked = isChecked,
        onCheckedChange = { newValue ->
            viewModel.updateFoodPreference(food, newValue)
        },
        modifier = Modifier.padding(4.dp)
    )
    Text(food, modifier = Modifier.padding(start = 8.dp))
}


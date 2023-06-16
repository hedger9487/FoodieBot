package com.foodiebot.app.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.foodiebot.app.R
import com.foodiebot.app.viewmodel.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onRegistrationSuccess: () -> Unit,
    onRegistrationFailure: (String) -> Unit
) {
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text(text = stringResource(R.string.label_name)) },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text(text = stringResource(R.string.label_email)) },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text(text = stringResource(R.string.label_password)) },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                viewModel.registerUser(
                    name = nameState.value,
                    email = emailState.value,
                    password = passwordState.value,
                    onSuccess = {
                        onRegistrationSuccess()
                    },
                    onFailure = { error ->
                        onRegistrationFailure(error)
                    }
                )
            }
        ) {
            Text(text = stringResource(R.string.btn_register))
        }
    }
}

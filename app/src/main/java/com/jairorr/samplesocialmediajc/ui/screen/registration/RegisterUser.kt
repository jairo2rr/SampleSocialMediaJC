package com.jairorr.samplesocialmediajc.ui.screen.registration

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import com.jairorr.samplesocialmediajc.data.User

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        var username by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            singleLine = true
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "New password") },
            singleLine = true,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val hiddenStateIcon =
                        if (passwordHidden) Icons.Filled.CheckCircle else Icons.Filled.AddCircle
                    val description = if (passwordHidden) "Show password" else "Hide  password"
                    Icon(imageVector = hiddenStateIcon, contentDescription = description)
                }
            }
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            registerUser(username, password) {
                password = ""
                username = ""
                Toast.makeText(context, "Register pressed", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Register user")
        }
    }
}

fun registerUser(username: String, password: String, clearOnSuccess: () -> Unit) {
    val database = FirebaseDatabase.getInstance().getReference("Users")
    val user = User(username, password)
    database.child(username).setValue(user).addOnSuccessListener {
        clearOnSuccess()
    }
}

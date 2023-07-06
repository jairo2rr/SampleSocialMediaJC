package com.jairorr.samplesocialmediajc.ui.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.withInfiniteAnimationFrameNanos
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jairorr.samplesocialmediajc.ui.MyScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.typeOf


@Composable
fun MyLoginScreen(viewModel: LoginViewModel, modifier: Modifier = Modifier,
navController: NavController) {
    var showLoginForm by rememberSaveable { mutableStateOf(true) }
    Surface(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showLoginForm) {
                Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                UserForm(
                    isCreateAccount = false
                ) { email, password ->
                    Log.d("TEST_LOGIN", "Logueando con $email y $password")
                    viewModel.sigInWithEmailAndPassword(email, password) {
                        navController.navigate(MyScreen.HomeScreen.route)
                    }
                }
            } else {
                Text(text = "Create an account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                UserForm(isCreateAccount = true) { email, password ->
                    Log.d("TEST_CREATE", "Creando cuenta con $email y $password")
                    viewModel.createAccountWithEmailAndPassword(email, password) {
                        navController.navigate(MyScreen.HomeScreen.route)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 =
                    if (showLoginForm) "Don't you have an account?" else "You have an account?"
                val text2 = if (showLoginForm) "Register here!" else "Login here!"
                Text(text = text1)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = text2, modifier = Modifier.clickable {
                        showLoginForm = !showLoginForm
                    },
                    color = Color.Blue
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    isCreateAccount: Boolean,
    operation: (String, String) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            singleLine = true,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val hiddenStateIcon =
                        if (passwordHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = hiddenStateIcon, contentDescription = description)
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {
            operation(email, password)
            email = ""
            password = ""
        }) {
            Text(text = if (isCreateAccount) "Create an account" else "Sign in")
        }

    }
}
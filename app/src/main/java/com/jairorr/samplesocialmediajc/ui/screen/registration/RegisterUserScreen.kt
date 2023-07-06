package com.jairorr.samplesocialmediajc.ui.screen.registration

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        var usersList by rememberSaveable{ mutableStateOf(emptyList<User>()) }
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
                        if (passwordHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = hiddenStateIcon, contentDescription = description)
                }
            }
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            registerUser(username, password) {
                //Limpiar los datos
                password = ""
                username = ""
                Toast.makeText(context, "Register pressed", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Register user")
        }
        Button(modifier = Modifier
            .fillMaxWidth(),onClick = { fetchData { newValue ->
            usersList = newValue
        } }
        ) {
            Text(text = "Get data from Firebase")
        }
        
        LazyColumn( modifier = Modifier.fillMaxWidth(),content = {
            items(usersList){
                Card(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()) {
                    Text(text = it.username?:"")
                    Text(text = it.password?:"")
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = { deleteUser(it.username) }) {
                        Text(text = "Delete user")
                    }
                }
            }
        })
    }
}

fun deleteUser(username: String?) {
    username?.let {
        val referenceDb = FirebaseDatabase.getInstance().getReference("Users").child(it)
        val removeUser = referenceDb.removeValue()
        removeUser.addOnSuccessListener {
            Log.d("STATECHILD","Remove successfully")
        }.addOnFailureListener{
            Log.d("STATECHILD","Can't remove it")
        }
    }
}

fun fetchData(changeUsers:(List<User>) -> Unit) {
    val database = FirebaseDatabase.getInstance().getReference("Users")
    database.addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val userList = mutableListOf<User>()
            Log.d("DONTFETCH", "Init")
            if(snapshot.exists()){
                Log.d("DONTFETCH", "Exists")
                for (empSnap in snapshot.children){
                    Log.d("DONTFETCH", "Before")
                    val emptUser = empSnap.getValue(User::class.java)
                    Log.d("DONTFETCH", "User $emptUser")
                    userList.add(emptUser!!)
                }
                changeUsers(userList)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("DONTFETCH", "Error might be founded $error")
        }

    })
}

fun registerUser(username: String, password: String, clearOnSuccess: () -> Unit) {
    val existUser = FirebaseDatabase.getInstance().getReference("Users")
    existUser.child(username).addListenerForSingleValueEvent(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()){
                Log.d("STATECHILD","Warning, this user exist")
            }else{
                Log.d("STATECHILD","All OK")
                val database = FirebaseDatabase.getInstance().getReference("Users")
                val user = User(username, password)
                database.child(username).setValue(user).addOnSuccessListener {
                    clearOnSuccess()
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("STATECHILD","Error appears: $error")
        }

    })


}

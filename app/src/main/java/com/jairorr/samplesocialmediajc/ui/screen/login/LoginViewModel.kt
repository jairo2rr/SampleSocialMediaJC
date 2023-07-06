package com.jairorr.samplesocialmediajc.ui.screen.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel:ViewModel() {

    val auth:FirebaseAuth = Firebase.auth
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    

    fun tryLogin(myEmail:String,myPassword:String){
        viewModelScope.launch {
            try {
                val auth = FirebaseAuth.getInstance().createUserWithEmailAndPassword(myEmail,myPassword)
                    .addOnCompleteListener{
                        Log.d("SIGNUP","Inside Complete listener")
                        Log.d("SIGNUP","Is successful? : ${it.isSuccessful}")
                        email = myEmail
                        password = myPassword
                    }.addOnFailureListener{
                        Log.d("SIGNUP","Inside Failure listener")
                        Log.d("SIGNUP","Exception : ${it.message}")
                    }
            }catch (e:Exception){
                val message = e.message
                Log.d("SIGNUP","Inside Exception : $message")
            }
        }
    }

    fun sigInWithEmailAndPassword(email:String,password:String, home:()->Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Log.d("TEST_SIGNIN","Login successful!")
                        home()
                    }else{
                        Log.d("TEST_SIGNIN","Failed: ${task.result}")
                    }
                }
        }catch (ex:Exception){
            Log.d("TEST_SINGIN","Exception: ${ex.message}")
        }
    }

    fun createAccountWithEmailAndPassword(email:String,password:String, home:()->Unit)
    = viewModelScope.launch {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Log.d("TEST_SIGNIN","Create successful!")
                        home()
                    }else{
                        Log.d("TEST_SIGNIN","Failed: ${task.result}")
                    }
                }
        }catch (ex:Exception){
            Log.d("TEST_SINGIN","Exception: ${ex.message}")
        }
    }

}
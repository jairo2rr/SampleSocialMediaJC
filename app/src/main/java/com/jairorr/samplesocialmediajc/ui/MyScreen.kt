package com.jairorr.samplesocialmediajc.ui

sealed class MyScreen(val route:String){
    object HomeScreen:MyScreen("home_screen")
    object RegistrationScreen:MyScreen("registration_screen")
}

package com.jairorr.samplesocialmediajc.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jairorr.samplesocialmediajc.BodyContainer
import com.jairorr.samplesocialmediajc.BodyScreen
import com.jairorr.samplesocialmediajc.ui.MyScreen
import com.jairorr.samplesocialmediajc.ui.screen.registration.RegisterUserScreen

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MyScreen.HomeScreen.route){
        composable(route = MyScreen.HomeScreen.route,){
            BodyContainer( viewModel = null,navController = navController)
        }
        composable(route = MyScreen.RegistrationScreen.route){
            RegisterUserScreen()
        }
    }
}
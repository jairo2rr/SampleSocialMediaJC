package com.jairorr.samplesocialmediajc.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jairorr.samplesocialmediajc.BodyContainer
import com.jairorr.samplesocialmediajc.BodyViewModel
import com.jairorr.samplesocialmediajc.ui.MyScreen
import com.jairorr.samplesocialmediajc.ui.screen.login.LoginViewModel
import com.jairorr.samplesocialmediajc.ui.screen.login.MyLoginScreen
import com.jairorr.samplesocialmediajc.ui.screen.registration.RegisterUserScreen

@Composable
fun MyNavigation(viewModel: BodyViewModel, viewLoginModel: LoginViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MyScreen.HomeScreen.route){
        composable(route = MyScreen.HomeScreen.route){
            BodyContainer( viewModel = viewModel,navController = navController)
        }
        composable(route = MyScreen.RegistrationScreen.route){
            RegisterUserScreen()
        }
        composable(route=MyScreen.LoginScreen.route){
            MyLoginScreen(viewModel = viewLoginModel, navController = navController)
        }
    }
}
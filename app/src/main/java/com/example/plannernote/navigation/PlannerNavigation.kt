package com.example.plannernote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plannernote.ui.theme.screens.Home
import com.example.plannernote.ui.theme.screens.login.PlannerLoginScreen

@Composable
fun PlannerNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PlannerScreens.PlannerHome.name
    ){
        composable(PlannerScreens.PlannerScreen.name){
           PlannerLoginScreen(navController = navController)
        }
        composable(PlannerScreens.PlannerHome.name){
           Home(navController = navController)
        }
    }
}
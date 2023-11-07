package com.example.plannernote.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.plannernote.navigation.PlannerScreens
import kotlinx.coroutines.delay

@Composable
fun PlannerSplashScreen(navController: NavHostController){
    LaunchedEffect(key1 = true){
        delay(2000L)
        navController.navigate(PlannerScreens.LoginScreen.name)
    }
    Text(text = "BIENVENIDE SEÑORR")
}
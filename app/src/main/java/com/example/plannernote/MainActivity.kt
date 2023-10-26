package com.example.plannernote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier


import androidx.navigation.NavType
import androidx.navigation.navArgument

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plannernote.ui.theme.PlannerNoteTheme
import com.example.plannernote.ui.theme.screens.LoguinName
import com.example.plannernote.ui.theme.screens.Registro

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlannerNoteTheme {
                // VARIABLE PARA USAR EL SISTEMA DE NAVEGACION, esta variable almacena donde estoy
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // FUNCION DEL SISTEMA DE NAVEGACION
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login"){
                            //LLAMO A LAS FUNCIONES DE MIS SCREENS
                           LoguinName(modifier = Modifier){

                           }
                        }
                        composable("registro"){
                            Registro(modifier = Modifier, onCreateUser = {
                                navController.navigate(
                                    route = "login/"
                                )
                            })
                        }
                    }
                }
            }
        }
    }
}

package com.example.plannernote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plannernote.ui.theme.PlannerNoteTheme
import com.example.plannernote.ui.theme.screens.LoguinName
import com.example.plannernote.ui.theme.screens.Registro
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {

    companion object{
        val TAG : String = MainActivity::class.java.simpleName
    }

    //INICIALIZO LA VIARABLE DEL FIREBASE
    private val auth by lazy {
        Firebase.auth
    }

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
                           LoguinName(modifier = Modifier, auth = Firebase){
                               // ACA ESTA LA LOGICA PARA ENTRAR DE LOGIN A REGISTRO
                               navController.navigate("registro")

                           }
                        }
                        composable("registro"){
                            Registro()
                            }
                        }
                    }
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    PlannerNoteTheme {
        LoguinName(modifier = Modifier, auth = Firebase) {

        }
    }

}

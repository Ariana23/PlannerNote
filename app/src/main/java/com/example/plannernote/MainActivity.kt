package com.example.plannernote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import androidx.navigation.compose.rememberNavController
import com.example.plannernote.navigation.PlannerNavigation
import com.example.plannernote.ui.theme.PlannerNoteTheme
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
                    PlannerApp()
                }
                }
            }
        }
}
@Composable
fun PlannerApp() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            PlannerNavigation()
        }

    }
}


/*
//CODIGO VIEJITO
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    PlannerNoteTheme {
        LoguinName(modifier = Modifier, auth = Firebase) {

        }
    }

}
*/
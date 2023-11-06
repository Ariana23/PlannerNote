package com.example.plannernote.ui.theme.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    // CREO LA VARIABLE AUTH. DE FIREBASE
    private val auth: FirebaseAuth = Firebase.auth

    // CREO LA VARIABLE LOADING, PARA IMPEDIR QUE SE CREEN VARIOS USUARIOS ACCIDENTALMENTE
    private val _loading = MutableLiveData(false)

    fun singInWithEmailAndPassword(email: String, password: String, home: ()-> Unit)
            = viewModelScope.launch {
        try{
            //ACA LLAMO A LA FUNCION QUE CONTIENE EL LOGEO
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->

                    // SI SE LOGEO BIEN, MANDA UN MENSAJE AL LOG

                    if (task.isSuccessful){
                        Log.d("Planner", "singInWith logueado!")
                        // LLAMO A LA FUNCION HOME
                        home()

                    }
                    else{
                        Log.d("Planner", "singInWith logueado!: ${task.result.toString()}")

                    }

                }
        }
        catch (ex:Exception){
            Log.d("Planner", "singInWith: ${ex.message}")
        }
    }




}
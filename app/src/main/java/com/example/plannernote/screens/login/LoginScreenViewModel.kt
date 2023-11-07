package com.example.plannernote.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            //ACA IMPLEMENTO LA BASE DE DATOS CLOUD FIRESTORE, CON ESTA VARIABLE, GUARDO LOS USUARIOS
                            val displayName =
                                task.result.user?.email?.split("")?.get(0)
                            createUser(displayName)

                            home()
                        }
                        else{
                            Log.d("Planner", "signInWithEmailAndPassword: ${task.result.toString()}")
                        }
                    }
            }
            catch (ex:Exception){
                Log.d("Planner", "signInWithEmailAndPassword:${ex.message}")
            }
        }

    private fun createUser(displayName: String?) {
        //RECUPERO EL ID CON LA AUTENTICACION DEL USUARIO, PARA ALMACENARLO
        val userId = auth.currentUser?.uid

        // MUTABLE MAP ME PERMITE CREAR  OBJETOS CON CLAVE Y VALOR
        val users = mutableMapOf<String, Any>()

        //ARMO LA COLECCION
        users["users_id"] = userId.toString()
        users["display_name"] = displayName.toString()

        //CON GET INSTANCE, PUEDO HACER LA REFERENCIA DE LA COLECCION QUE ARME
        FirebaseFirestore.getInstance().collection("users")
            .add(users)
            .addOnSuccessListener {
                Log.d("Planner", "creado ${it.id}")
            }.addOnFailureListener{
                Log.d("Planner", "Error ${it}")
            }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    ){
       if (_loading.value == false){
           _loading.value = true
           auth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener { task ->
                   if (task.isSuccessful){
                       home()
                   }
                   else
                       Log.d("Planner", "createUserWithEmailAndPassword:${task.result.toString()}")
               }
           _loading.value = false
       }
    }






}
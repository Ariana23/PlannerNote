package com.example.plannernote.ui.theme.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import android.annotation.SuppressLint
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.plannernote.MainActivity.Companion.TAG
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
// ACA AGREGO EL onCreate para el sistema de navegacion
fun LoguinName(
    auth: Firebase,
    modifier: Modifier,
    onCreateUser: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    val isEmailValid by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }
    val isPasswordValid by derivedStateOf {
        password.length > 7
    }
    var isPasswordVisible by remember{
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Card(modifier = Modifier
            .weight(2f)
            .fillMaxWidth()
            .padding(all = 16.dp))
        {

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 32.dp
                ), horizontalAlignment = Alignment.CenterHorizontally) {

                //TEXO BIENVENIDO
                Text(
                    text = "Bienvenidos:",
                    fontSize = 30.sp,
                )
                // CAJA PEDIR NOMBRE
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Usuario") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Down)}
                    ),
                    isError = !isPasswordValid,
                    trailingIcon = {
                        if (email.isNotBlank()){
                            IconButton(onClick = { email = "" }) {
                                Icon(imageVector = Icons.Filled.Clear ,
                                    contentDescription = "Clear email" )

                            }
                        }
                    }

                )


                // CAJA PEDIR CONTRASE

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.clearFocus()}
                    ),
                    isError = !isPasswordValid,
                    trailingIcon = {
                        // ICONO DEL OJITO
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(imageVector = if(isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Restablecer Contraseña",
                        color = Color.Black,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(end = 8.dp)
                        )

                }

                Button(onClick = {
                    onCreateUser()
                }) {
                    //ACA TIENE QUE RECIBIR LA VARIABLE propuesta_nombre
                    Text(text = "REGISTRATE")
                }
                Button(onClick = {
                    //ESTO LO HCE MAL
                auth.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.d(TAG, "The user has successfully logged in")
                        } else{
                            Log.d(TAG,"The use has FAILED", it.exception)
                        }
                    }
                },


                    enabled = isEmailValid && isPasswordValid)
                {
                        Text(text = "INGRESAR")
                }
                
            }
        }
    }
}


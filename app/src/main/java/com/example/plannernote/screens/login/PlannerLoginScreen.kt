package com.example.plannernote.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.plannernote.navigation.PlannerScreens
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


//CUANDO CREO UNA CUENTA, TIENE QUE SER VERDADERO EL EMAIL, PORQUE SI NO ROMPE!!!



@Composable
fun PlannerLoginScreen(navController: NavController,
                       viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                       ){
    //ESTE FORMULARIO VALIDA SI TENGO UNA CUENTA O LA TENGO QUE CREAR
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(modifier = Modifier.fillMaxSize())
    {

    }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
           )
        {
            if (showLoginForm.value){
                Text(text = "Inciar sesión")
                UserForm(
                    isCreateAccount = false
                ){
                    email, password ->
                    Log.d("Planner","Logueando con $email, $password")
                    viewModel.signInWithEmailAndPassword(email,password){
                        navController.navigate(PlannerScreens.PlannerHomeScreen.name)
                    }
                }
            }
            else{
                Text(text = "Crear una cuenta nueva")
                UserForm(
                    isCreateAccount = true
                ){
                        email, password ->
                    Log.d("Planner","Creando cuando con $email, $password")
                    viewModel.createUserWithEmailAndPassword(email,password){
                        navController.navigate(PlannerScreens.PlannerHomeScreen.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 =
                    if(showLoginForm.value) "¿No tienes cuenta?"
                        else "Ya tienes cuenta?"
                val text2 =
                    if (showLoginForm.value) "Registrate"
                else "Inicia sesion"
                Text(text = text1)
                Text(text = text2,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    color = androidx.compose.material.MaterialTheme.colors.secondaryVariant
                    )

            }
        }
    }
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String,String) -> Unit = {email, pwd ->}
) {
    // ESTA VARIABLE ALCEMA EL ESTADO DEL EMAIL
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable {
        mutableStateOf(false)

    }

    val valido = remember(email.value, password.value){
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally)

    {
        EmailInput(
            emailState = email
        )
        PasswordInput(
            passwordState = password,
            labelId = "Password",
            passwordVisible = passwordVisible
        )
        SubmitButton(
            textId = if (isCreateAccount) "Crear Cuenta" else "Login",
            inputValido = valido
        )
        {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }


        Spacer(modifier = Modifier.height(15.dp))
        ClickableText(
            text = AnnotatedString("Forzar cierre Crashlytics"),
            onClick = {
                //ESTO HACE QUE SE CIERRE LA APP CUANDO PRESIONO EL BOTON
                throw RuntimeException("Error forzado desde LoginScreens")
            }
        )

    }
}
@Composable
fun SubmitButton(
    textId: String,
    inputValido: Boolean,
    onClic: ()->Unit
){
    Button(onClick = onClic,
       modifier = Modifier
           .padding(3.dp)
           .fillMaxWidth(),
        enabled = inputValido
        ) {
        Text(text = textId,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

/*
@Composable
fun SubmitButton(
    textId: String,
    inputValido: Boolean,
    onClic: ()-> Unit
)
{
    //ACA LE PASO EL COMPONENTE
    Button(onClick = onClic,
        modifier = Modifier.padding(3.dp),
        enabled = inputValido
    ) {
        Text(text = textId,
            modifier = Modifier
                .padding(5.dp))

    }
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
) {
    val visualTransformation = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        value =passwordState.value,
        onValueChange = {passwordState.value = it},
        label = { Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()){
                PasswordVisibleIcon(passwordVisible)
            }
            else null
        }
    )

}

@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>

) {
    val image =
        if (passwordVisible.value)
            Icons.Default.VisibilityOff
        else
            Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(imageVector = image ,
            contentDescription = "")

    }
}






@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
    ) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    valueState: MutableState<String>, 
    labelId: String, 
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = valueState.value , 
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
        )
}

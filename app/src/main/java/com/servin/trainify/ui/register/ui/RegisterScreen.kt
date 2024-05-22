package com.servin.trainify.ui.register.ui

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.servin.trainify.R
import com.servin.trainify.ui.theme.Red
import com.servin.trainify.ui.theme.RedSurface
import com.servin.trainify.viewmodel.RegisterViewModel
import com.servin.trainify.viewmodel.UsuariosViewModel

@Composable
fun RegisterScreen(navController: NavController,viewModel: RegisterViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Register(navController,viewModel)
    }

}


@Composable
fun Register(navController: NavController,viewModel: RegisterViewModel) {

    val firstName : String by viewModel.firstName.observeAsState("")
    val lastName : String by viewModel.lastName.observeAsState("")
    val email : String by viewModel.email.observeAsState("")
    val password : String by viewModel.password.observeAsState("")

    val registerState : Boolean by viewModel.registerState.observeAsState(false)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Top()
        WelcomeMessage()
        Spacer(modifier = Modifier.size(10.dp))
        Instructions()
        Spacer(modifier = Modifier.size(10.dp))
        FirstName(firstName){viewModel.onRegisterChanged(it, lastName, email, password)}
        Spacer(modifier = Modifier.size(10.dp))
        LastName(lastName){viewModel.onRegisterChanged(firstName, it, email, password) }
        Spacer(modifier = Modifier.size(10.dp))
        Email(email){viewModel.onRegisterChanged(firstName, lastName, it, password) }
        Spacer(modifier = Modifier.size(10.dp))
        Password(password){viewModel.onRegisterChanged(firstName, lastName, email, it)}
        Spacer(modifier = Modifier.size(10.dp))
        RegisterButton(registerState)
        Spacer(modifier = Modifier.size(10.dp))
        AlreadyAccount(navController)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Top() {
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "",
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun WelcomeMessage() {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f), color = Color(RedSurface.toArgb())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Registro",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}

@Composable
fun Instructions() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Por favor ingresa tus datos para registrarte",
        fontSize = 15.sp
    )

}

@Composable
fun FirstName(firstName: String, onFirstNameChanged: (String) -> Unit){
    Text(text = "Nombre", modifier = Modifier.fillMaxWidth())
    OutlinedTextField(
        value = firstName,
        onValueChange = {onFirstNameChanged(it) },
        label = {
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp)
    )
}

@Composable
fun LastName(lastName:String, onLastNameChanged: (String) -> Unit){
    Text(text = "Apellido", modifier = Modifier.fillMaxWidth())
    OutlinedTextField(
        value = lastName,
        onValueChange = { onLastNameChanged(it)},
        label = {
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp)
    )
}

@Composable
fun Email(email:String,onEmailChanged:(String) -> Unit){
    Text(text = "Correo ", modifier = Modifier.fillMaxWidth())

    OutlinedTextField(
        value = email,
        onValueChange = {onEmailChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp)
    )
}

@Composable
fun RegisterButton(registerState:Boolean) {

    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(Color(Red.toArgb())),
        enabled = registerState
    ) {
        Text(text = "Registrarse")

    }

}

@Composable
fun AlreadyAccount(navController: NavController) {
    Text(
        text = "¿Ya tienes una cuenta? Inicia sesión",
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable { navController.navigate("login") }

    )

}

@Composable
fun Password(password:String, onPasswordChanged:(String) -> Unit){
    Text(text = "Contraseña", modifier = Modifier.fillMaxWidth())
    OutlinedTextField(
        value = password,
        onValueChange = {onPasswordChanged(it)},
        label = {
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp)
    )

}



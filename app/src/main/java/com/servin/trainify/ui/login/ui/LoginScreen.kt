package com.servin.trainify.ui.login.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.servin.trainify.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.servin.trainify.model.DataResponse
import com.servin.trainify.navigation.Screens
import com.servin.trainify.ui.theme.RedPrimary
import com.servin.trainify.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Login(Modifier.align(Alignment.Center), viewModel, navController)


    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {

    val email: String by viewModel.email.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val loginEnable: Boolean by viewModel.loginState.observeAsState(false)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        EmailTextField(email) { viewModel.onLoginChanged(it, password) }
        PasswordTextField(password) { viewModel.onLoginChanged(email, it) }
        Buttons(loginEnable, { viewModel.onLoginSelected() }, navController,viewModel)
        ForgotPassword()
        GuestLogin(navController)
        LoadingScreen(viewModel,navController)


    }

}

@Composable
fun ForgotPassword() {
    Text(text = "¿Olvidaste tu contraseña?", modifier = Modifier.padding(top = 20.dp))
}


@Composable
fun Buttons(loginEnable: Boolean, onLoginChanged: () -> Unit, navController: NavController,viewModel: LoginViewModel) {
    Button(
        onClick = { viewModel.onLoginSelected()},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        colors = ButtonDefaults.buttonColors(RedPrimary), enabled = loginEnable
    ) {
        Text(text = "Iniciar Sesión")

    }
    Button(
        onClick = { navController.navigate(Screens.REGISTER) }, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        colors = ButtonDefaults.buttonColors(RedPrimary)
    ) {
        Text(text = "Registrarse")

    }
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "",
        modifier = Modifier.size(230.dp)
    )
}

@Composable
fun EmailTextField(email: String, onLoginChanged: (String) -> Unit) {

    OutlinedTextField(
        value = email,
        onValueChange = { onLoginChanged(it) },
        label = { Text(text = "Correo") },
        shape = RoundedCornerShape(30.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.email),
                contentDescription = ""
            )
        },
        modifier = Modifier.fillMaxWidth()
    )

}


@Composable
fun PasswordTextField(password: String, onLoginChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onLoginChanged(it) },
        label = { Text(text = "Contraseña") },
        shape = RoundedCornerShape(30.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.email),
                contentDescription = ""
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    )

}

@Composable
fun GuestLogin(navController: NavController) {
    Text(
        text = "Continuar como invitado",
        modifier = Modifier
            .padding(top = 20.dp)
            .clickable { navController.navigate(Screens.HOME) },
        color = Color.Black,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,

    )
}

@Composable fun LoadingScreen(viewModel: LoginViewModel,navController: NavController){
    when(viewModel.stateLogin) {
        is DataResponse.Success -> {
            navController.navigate(Screens.HOME)
        }
        is DataResponse.Error -> {
            Toast.makeText(LocalContext.current,"Error",Toast.LENGTH_SHORT).show()

        }
        DataResponse.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else ->{
            //Do nothing
        }
    }
}



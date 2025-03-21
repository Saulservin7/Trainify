package com.servin.trainify.auth.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.R
import com.servin.trainify.auth.presentation.viewmodel.AuthState
import com.servin.trainify.auth.presentation.viewmodel.AuthViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val authState by viewModel.authstate.collectAsState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp) // Padding horizontal general

    ) {
        val (logo, emailTextField, passwordTextField, login, newUser, guideline) = createRefs()

        // Guía vertical para centrado (50% de la altura)
        val topLine = createGuidelineFromTop(0.15f)


        // Logo centrado en la parte superior
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .size(250.dp)
                .constrainAs(logo) {
                    top.linkTo(topLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Campos de texto centrados
         OutlinedTextField(
            value = email,
            onValueChange = { viewModel.setEmail(it) },
            label = { Text("Correo electrónico") },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(emailTextField) {
                    top.linkTo(logo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.setPassword(it) },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(passwordTextField) {
                    top.linkTo(emailTextField.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Botón anclado a la parte inferior
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(login) {
                    bottom.linkTo(newUser.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Iniciar sesión", color = Color.White)
        }

        // Texto en la parte inferior
        Text(
            text = "¿Eres nuevo? Regístrate",
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .constrainAs(newUser) {
                    bottom.linkTo(parent.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable { onRegisterClick() }
        )
    }
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onLoginClick()
        }
    }
}


package com.servin.trainify.auth.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.auth.presentation.viewmodel.AuthState
import com.servin.trainify.auth.presentation.viewmodel.AuthViewModel
import com.servin.trainify.ui.theme.BluePrimary
import com.servin.trainify.ui.theme.BlueSecondary

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val nameError by viewModel.errorName.collectAsState()
    val emailError by viewModel.errorMail.collectAsState()
    val passwordError by viewModel.errorPassword.collectAsState()
    val confirmPasswordError by viewModel.errorConfirmPassword.collectAsState()
    val authState by viewModel.authstate.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Scroll en el ConstraintLayout
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            val (title, indications, columnform, registerButton, alreadyAccount) = createRefs()
            Text(
                text = "Crear cuenta",
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                color = BluePrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 40.dp)
                    start.linkTo(parent.start)

                }
            )

            Text(
                fontSize = 16.sp,
                modifier = Modifier
                    .constrainAs(indications) {
                        top.linkTo(title.bottom, margin = 10.dp)
                        start.linkTo(parent.start)

                    },
                text = "Rellena los campos para crear tu cuenta",
            )

            Column(
                modifier = Modifier
                    .constrainAs(columnform) {
                        top.linkTo(indications.bottom, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(alreadyAccount.top)
                    }
                    .fillMaxSize()
            ) {
                FieldForm(
                    "Nombre", "Escribe tu nombre", name, { viewModel.setName(it) },
                    isError = nameError
                )
                FieldForm(
                    "Correo Electronico",
                    "Escribe tu correo electronico",
                    email,
                    { viewModel.setEmail(it) },
                    isError = emailError
                )
                FieldForm(
                    "Contraseña",
                    "Escribe tu contraseña",
                    password,
                    { viewModel.setPassword(it) },
                    isError = passwordError,
                    isPassword = true
                )

                FieldForm(
                    "Confirma tu contraseña",
                    "Escribe de nuevo tu contraseña",
                    confirmPassword,
                    { viewModel.confirmPassword(it) },
                    isError = confirmPasswordError,
                    isPassword = true
                )
                Button(
                    onClick = { viewModel.register(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary,
                        contentColor = Color.White
                    ), shape = RectangleShape
                ) {
                    Text("Registrarse", fontSize = 16.sp)
                }


            }

            Text(
                "¿Ya tienes una cuenta?",
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.constrainAs(alreadyAccount) {

                    bottom.linkTo(registerButton.bottom, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Button(
                onClick = { /* Lógica de registro */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(registerButton) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, shape = RectangleShape,

            ) {
                Text("Iniciar Sesión", fontSize = 16.sp, color = Color.White)
            }


        }

    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onRegisterSuccess()
        }
    }

}


@Composable
fun FieldForm(
    title: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    isPassword: Boolean = false
) {
    Text(text = title, modifier = Modifier.padding(bottom = 10.dp))
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        textStyle = if (isError) TextStyle(color = Color.Red) else TextStyle.Default
    )
}



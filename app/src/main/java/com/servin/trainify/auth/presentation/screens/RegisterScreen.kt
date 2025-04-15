package com.servin.trainify.auth.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.auth.presentation.components.FieldForm
import com.servin.trainify.auth.presentation.viewmodel.AuthState
import com.servin.trainify.auth.presentation.viewmodel.AuthViewModel
import com.servin.trainify.ui.theme.BluePrimary

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
    val passwordHidden by viewModel.passwordHiddenState.collectAsState()
    val confirmPasswordHidden by viewModel.confirmPasswordHiddenState.collectAsState()


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
                    isError = nameError,
                    errorDescription = "Tu nombre no debe contener números ni caracteres especiales",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
                FieldForm(
                    "Correo Electronico",
                    "Escribe tu correo electronico",
                    email,
                    { viewModel.setEmail(it) },
                    isError = emailError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    errorDescription = "El correo electronico debe ser gmail,outlook o hotmail"
                )
                FieldForm(
                    "Contraseña",
                    "Escribe tu contraseña",
                    password,
                    { viewModel.setPassword(it) },
                    isError = passwordError,
                    isPassword = true,
                    passwordHidden = passwordHidden,
                    onPasswordVisibilityToggle = { viewModel.togglePasswordVisibility() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    errorDescription = "La contraseña debe tener al menos 8 caracteres"
                )

                FieldForm(
                    "Confirma tu contraseña",
                    "Escribe de nuevo tu contraseña",
                    confirmPassword,
                    { viewModel.confirmPassword(it) },
                    isError = confirmPasswordError,
                    isPassword = true,
                    passwordHidden = confirmPasswordHidden,
                    onPasswordVisibilityToggle = { viewModel.toggleConfirmPasswordVisibility() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    errorDescription = "Las contraseñas no coinciden"
                )
                Button(
                    onClick = { viewModel.register(email, password,name) },
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
                    },
                shape = RectangleShape,

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






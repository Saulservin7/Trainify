package com.servin.trainify.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.servin.trainify.R
import com.servin.trainify.ui.theme.Red

@Composable
fun LoginScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)){
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.app_logo), contentDescription ="",modifier = Modifier.size(230.dp) )
            OutlinedTextField(value = "", onValueChange ={}, label = { Text(text = "Correo")}, shape = RoundedCornerShape(30.dp),
                leadingIcon = { Image(painter = painterResource(id = R.drawable.email), contentDescription = "")}
                ,modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(value = "", onValueChange ={}, label = { Text(text = "Contraseña")}, shape = RoundedCornerShape(30.dp),
                leadingIcon = { Image(painter = painterResource(id = R.drawable.email), contentDescription = "")}
                ,modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            )

            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().padding(top = 50.dp)
            ,colors = ButtonDefaults.buttonColors(Color (Red.toArgb()))){
                Text(text = "Iniciar Sesión")
                
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(Color (Red.toArgb()))){
                Text(text = "Registrarse")

            }

            Text(text = "¿Olvidaste tu contraseña?", modifier = Modifier.padding(top = 20.dp))

        }
    }
}


@Composable
@Preview (showBackground = true)
fun PreviewLoginScreen(){
    LoginScreen()
}
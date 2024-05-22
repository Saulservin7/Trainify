package com.servin.trainify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.servin.trainify.navigation.NavManager
import com.servin.trainify.ui.login.ui.LoginScreen
import com.servin.trainify.viewmodel.LoginViewModel
import com.servin.trainify.ui.theme.TrainifyTheme
import com.servin.trainify.viewmodel.RegisterViewModel
import com.servin.trainify.viewmodel.UsuariosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainifyTheme {
                // A surface container using the 'background' color from the theme
                val loginViewModel = LoginViewModel()
                val registerViewModel = RegisterViewModel()

                NavManager(loginViewModel,registerViewModel)
            }
        }
    }
}


package com.servin.trainify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.servin.trainify.navigation.NavManager
import com.servin.trainify.ui.login.ui.LoginScreen
import com.servin.trainify.viewmodel.LoginViewModel
import com.servin.trainify.ui.theme.TrainifyTheme
import com.servin.trainify.viewmodel.ExerciseViewModel
import com.servin.trainify.viewmodel.HomeViewModel
import com.servin.trainify.viewmodel.RegisterViewModel
import com.servin.trainify.viewmodel.UsuariosViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel : LoginViewModel by viewModels()
    private val registerViewModel : RegisterViewModel by viewModels()
    private  val exerciseViewModel : ExerciseViewModel by viewModels()
    private val homeViewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainifyTheme {
                NavManager(loginViewModel,registerViewModel,exerciseViewModel,homeViewModel)
            }
        }
    }
}


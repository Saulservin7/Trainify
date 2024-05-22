package com.servin.trainify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.servin.trainify.ui.login.ui.LoginScreen
import com.servin.trainify.ui.register.ui.RegisterScreen
import com.servin.trainify.viewmodel.LoginViewModel
import com.servin.trainify.viewmodel.RegisterViewModel
import com.servin.trainify.viewmodel.UsuariosViewModel


@Composable
fun NavManager(loginViewModel: LoginViewModel,registerViewModel: RegisterViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screens.LOGIN) {
        composable(Screens.LOGIN) {
            LoginScreen(loginViewModel,navController)
        }
        composable(Screens.REGISTER) {
            RegisterScreen(navController,registerViewModel)
        }
    }
}
package com.servin.trainify.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.servin.trainify.auth.presentation.screens.LoginScreen
import com.servin.trainify.auth.presentation.screens.RegisterScreen
import com.servin.trainify.navbar.components.BottomBar
import com.servin.trainify.presentation.screens.home.HomeScreen
import com.servin.trainify.presentation.screens.profile.ProfileScreen
import com.servin.trainify.presentation.screens.settings.SettingsScreen

@Composable
fun NavigationWrapper(

) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Obtener la ruta actual
    val currentRoute = navBackStackEntry?.destination?.route?.substringAfterLast("/")

    // Definir rutas donde debe aparecer el BottomBar
    val bottomBarRoutes = listOf(
        HomeDestination::class.simpleName,
        ProfileDestination::class.simpleName,
        SettingsDestination::class.simpleName
    )

    Scaffold(
        bottomBar = {

            if (currentRoute in bottomBarRoutes) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = LoginDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LoginDestination> {
                LoginScreen(
                    onRegisterClick = { navController.navigate(RegisterDestination) },
                    onLoginClick = { navController.navigate(HomeDestination) }
                )
            }
            composable<RegisterDestination> {
                RegisterScreen(
                    onLoginClick = { navController.navigate(LoginDestination) },
                    onRegisterSuccess = { navController.navigate(HomeDestination) }
                )
            }
            composable<HomeDestination> {
                HomeScreen(
                    onLogoutClick = { navController.navigate(LoginDestination) }
                )
            }

            composable <ProfileDestination>{
                ProfileScreen()
            }
            composable<SettingsDestination>{
                SettingsScreen()
            }
        }
    }
}


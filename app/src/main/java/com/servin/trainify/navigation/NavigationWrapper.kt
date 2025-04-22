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
import com.servin.trainify.exercises.presentation.screens.AddExerciseScreen
import com.servin.trainify.navigation.components.BottomBar
import com.servin.trainify.presentation.screens.home.HomeScreen

import com.servin.trainify.presentation.screens.settings.SettingsScreen
import com.servin.trainify.profile.presentation.screens.EditProfileScreen
import com.servin.trainify.profile.presentation.screens.ProfileScreen


// navigation/NavigationWrapper.kt
// navigation/NavigationWrapper.kt
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            if (currentRoute != AppDestination.Login.route && currentRoute != AppDestination.Register.route) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestination.Login.route) {
                LoginScreen(
                    onRegisterClick = { navController.navigate(AppDestination.Register.route) },
                    onLoginClick = {
                        navController.navigate(AppDestination.Home.route) {
                            popUpTo(AppDestination.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppDestination.Register.route) {
                RegisterScreen(
                    onLoginClick = { navController.navigate(AppDestination.Login.route) },
                    onRegisterSuccess = {
                        navController.navigate(AppDestination.Home.route) {
                            popUpTo(AppDestination.Register.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppDestination.Home.route) {
                HomeScreen(
                    onLogoutClick = { navController.navigate(AppDestination.Login.route)
                    },
                    navigate = { navController.navigate(AppDestination.addExercise.route) }
                )
            }

            composable(AppDestination.Profile.route) {
               ProfileScreen(
                    onEditProfileClick = { navController.navigate(AppDestination.EditProfile.route) },
               )
            }

            composable(AppDestination.Settings.route) {
                SettingsScreen()
            }

            composable(AppDestination.EditProfile.route) {
                EditProfileScreen(
                    onSaveChangesClick = { navController.navigate(AppDestination.Profile.route) }
                )
            }

            composable(AppDestination.addExercise.route) {
                AddExerciseScreen()
            }
        }
    }
}

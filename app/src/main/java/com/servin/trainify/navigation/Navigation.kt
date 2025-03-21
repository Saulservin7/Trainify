package com.servin.trainify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.servin.trainify.presentation.screens.home.HomeScreen
import com.servin.trainify.auth.presentation.screens.LoginScreen
import com.servin.trainify.auth.presentation.screens.RegisterScreen
import com.servin.trainify.presentation.screens.exercises.AddExerciseView
import com.servin.trainify.presentation.screens.profile.ProfileScreen
import com.servin.trainify.presentation.screens.settings.SettingsScreen


@Composable
fun Navigation(navController: NavHostController) {
   NavHost(navController, startDestination = Routes.Login.route) {
         composable(Routes.Login.route) {
              LoginScreen(
                    onRegisterClick = {
                        navController.navigate(Routes.Register.route)
                    },
                    onLoginClick = {
                        navController.navigate(Routes.Home.route)
                    }
              )
         }
         composable(Routes.Register.route) {
              RegisterScreen(
                    onLoginClick = {
                        navController.navigate(Routes.Login.route)
                    },
                    onRegisterSuccess = {
                        navController.navigate(Routes.Home.route)
                    }
              )
         }
         composable(Routes.Home.route) {
              HomeScreen(
                    onLogoutClick = {
                        navController.navigate(Routes.Login.route)
                    }
              )
         }

       composable(Routes.Profile.route) {
           ProfileScreen()
       }
       composable(Routes.Settings.route) {
           SettingsScreen()
       }

       composable(Routes.Exercise.route) {
           AddExerciseView()
       }

   }

}
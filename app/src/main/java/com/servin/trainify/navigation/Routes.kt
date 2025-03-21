package com.servin.trainify.navigation

import android.graphics.drawable.Icon

sealed class Routes(val route: String) {

    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object Exercise : Routes("exercise")
    object Profile : Routes("profile")
    object Settings : Routes("settings")
    object Training : Routes("training")

}
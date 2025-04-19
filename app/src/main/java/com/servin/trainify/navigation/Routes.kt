package com.servin.trainify.navigation
import com.servin.trainify.R



sealed class AppDestination(val route: String){
    object Home : AppDestination("home")
    object Profile : AppDestination("profile")
    object Settings : AppDestination("settings")
    object Login : AppDestination("login")
    object Register : AppDestination("register")
}

// navigation/BottomBarItem.kt
sealed class BottomBarItem(
    val destination: AppDestination,  // Usamos la clase sellada
    val title: String,
    val icon: Int
) {
    object Home : BottomBarItem(
        destination = AppDestination.Home,
        title = "Inicio",
        icon = R.drawable.home
    )

    object Profile : BottomBarItem(
        destination = AppDestination.Profile,
        title = "Perfil",
        icon = R.drawable.profile
    )

    object Settings : BottomBarItem(
        destination = AppDestination.Settings,
        title = "Configuración",
        icon = R.drawable.settings
    )

    companion object {
        val entries = listOf(Home, Profile, Settings)
    }

}
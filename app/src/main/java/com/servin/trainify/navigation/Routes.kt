package com.servin.trainify.navigation


import com.servin.trainify.R
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

@Serializable
object RegisterDestination

@Serializable
object HomeDestination

@Serializable
object ProfileDestination

@Serializable
object SettingsDestination

sealed class BottomBarItem(
    val route: Any, // Usamos los objetos serializables directamente
    val title: String,
    val pattern: String,
    val icon: Int
) {
    object Home : BottomBarItem(
        route = HomeDestination,
        title = "Inicio",
        icon = R.drawable.home,
        pattern = "homedestination"
    )

    object Profile : BottomBarItem(
        route = ProfileDestination,
        title = "Perfil",
        icon = R.drawable.profile,
        pattern = "profiledestination"
    )

    object Settings : BottomBarItem(
        route = SettingsDestination,
        title = "Configuración",
        icon = R.drawable.settings,
        pattern = "settingsdestination"
    )

    companion object {
        val entries = listOf(Home, Profile, Settings)
    }
}
package com.servin.trainify.navbar.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.servin.trainify.R
import com.servin.trainify.navigation.Routes
import com.servin.trainify.ui.theme.BluePrimary
import com.servin.trainify.ui.theme.Gray

@Composable

fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier.fillMaxWidth().fillMaxHeight(0.1f),
        containerColor = Gray,
        tonalElevation = 100.dp
    ) {
        listOf(Routes.Home, Routes.Profile, Routes.Settings).forEach { destination ->
            val selected = currentRoute == destination.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    NavBarIcon(
                        route = destination,
                        isSelected = selected
                    )
                },
                label = {
                    NavBarLabel(
                        route = destination,
                        isSelected = selected
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    indicatorColor = BluePrimary
                )
            )
        }
    }
}

@Composable
private fun NavBarIcon(route: Routes, isSelected: Boolean) {
    val icon: Painter? = when (route) {
        Routes.Home -> painterResource(R.drawable.home)
        Routes.Profile -> painterResource(R.drawable.profile)
        Routes.Settings -> painterResource(R.drawable.settings)
        else -> null
    }

    icon?.let {
        Icon(
            painter = it,
            contentDescription = null,
            tint = if (isSelected) {
                Color.White
            } else {
                Color.White
            },
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun NavBarLabel(route: Routes, isSelected: Boolean) {
    val label = when (route) {
        Routes.Home -> "Inicio"
        Routes.Exercise -> "Ejercicios"
        Routes.Profile -> "Perfil"
        Routes.Login -> "Iniciar Sesión"
        Routes.Register -> "Registro"
        Routes.Settings -> "Configuración"
        Routes.Training -> "Entrenamiento"
    }

    Text(
        text = label,
        color = if (isSelected) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        fontSize = 10.sp,
        maxLines = 1
    )
}



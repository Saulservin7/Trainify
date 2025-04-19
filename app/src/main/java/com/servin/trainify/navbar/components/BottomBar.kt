package com.servin.trainify.navbar.components

import android.util.Log
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
import androidx.compose.runtime.collectAsState
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
import com.servin.trainify.navigation.BottomBarItem
import com.servin.trainify.navigation.NavigationManager

import com.servin.trainify.ui.theme.BluePrimary
import com.servin.trainify.ui.theme.Gray

// components/BottomBar.kt
@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Obtenemos la ruta como string del destino actual
    val currentRoute = currentDestination?.route?.substringAfterLast(".")?.lowercase() // Extraer el último segmento y convertir a minúsculas

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        containerColor = Gray,
        tonalElevation = 100.dp
    ) {
        BottomBarItem.entries.forEach { destination ->
            val isSelected = currentRoute == destination.title
            Log.d("BottomBar", "Current route: $currentRoute, Destination: ${destination.destination}, Is selected: $isSelected")

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(destination.destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = destination.title,
                        tint = if (isSelected) BluePrimary else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(
                        text = destination.title,
                        color = if (isSelected) BluePrimary else Color.White,
                        fontSize = 10.sp
                    )
                }
            )
        }
    }
}





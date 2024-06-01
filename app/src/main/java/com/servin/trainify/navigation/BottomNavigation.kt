package com.servin.trainify.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.servin.trainify.ui.theme.RedPrimary

@Composable
fun BottomNavigation(navController: NavController) {
    val screens = listOf(
        Routes.Home,
        Routes.Exercises,
        Routes.Config
    )

    BottomAppBar(

    ) {
        NavigationBar(containerColor = RedPrimary) {
            screens.forEach() { screens ->
                NavigationBarItem(
                    selected = navController.currentBackStackEntryAsState().value?.destination?.route == screens.route,
                    onClick = { navController.navigate(screens.route) },
                    icon = { Text(text = screens.title, color = Color.White)})
            }
        }
    }
}


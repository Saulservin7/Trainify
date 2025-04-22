package com.servin.trainify.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() { // Constructor público con @Inject
    private var navController: NavHostController? = null
    private val _currentRoute = MutableStateFlow<String?>(null)
    val currentRoute: StateFlow<String?> = _currentRoute

    fun setNavController(controller: NavHostController) {
        navController = controller
    }

    fun navigate(route: String) {
        navController?.navigate(route) {
            _currentRoute.value = route
        }
    }

    fun navigateUp() {
        navController?.navigateUp()
    }

    fun popBackStack() {
        navController?.popBackStack()
    }

    fun navigateAndClearBackStack(route: String, popUpToRoute: String) {
        navController?.navigate(route) {
            popUpTo(popUpToRoute) {
                inclusive = true
            }
        }
        _currentRoute.value = route
    }
}
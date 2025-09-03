package org.practice.project.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.practice.project.navigation.auth.AuthNavGraph
import org.practice.project.navigation.auth.LoginScreen
import org.practice.project.navigation.auth.RegisterScreen
import org.practice.project.navigation.main.BottomBar
import org.practice.project.navigation.main.mainNavGraph

// RootNavGraph.kt - VERSIÓN CORREGIDA
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RootNavGraph(
    startDestination: String = Destinations.LOGIN
) {
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) }



    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                BottomBar(
                    currentDestination = currentDestination,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // ✅ CORRECTO: Auth como composable individual
            composable(Destinations.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Destinations.MAIN) {
                            popUpTo(Destinations.LOGIN) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Destinations.REGISTER)
                    }
                )
            }

            composable(Destinations.REGISTER) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Destinations.MAIN) {
                            popUpTo(Destinations.LOGIN) { inclusive = true }
                        }
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            // ✅ CORRECTO: Main como navigation anidado
            navigation(
                startDestination = Destinations.HOME,
                route = Destinations.MAIN
            ) {
                mainNavGraph(
                    navController = navController,
                    showBottomBar = { showBottomBar = it }
                )
            }
        }
    }
}
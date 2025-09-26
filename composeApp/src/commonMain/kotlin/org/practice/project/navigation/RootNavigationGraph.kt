package org.practice.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.practice.project.navigation.auth.authNavGraph
import org.practice.project.navigation.main.home.HomeScreen

// RootNavGraph.kt - VERSIÓN CORREGIDA
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = RootGraph.ROOT,
        startDestination = RootGraph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(RootGraph.HOME){
            HomeScreen()
        }
    }
}


object RootGraph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
    const val SETTINGS = "SETTINGSSUB"
    const val MAP = "MAP_SCREEN"
    const val DATETIME = "DATETIME"
    const val SIGNATURE = "SIGNATURE"
}
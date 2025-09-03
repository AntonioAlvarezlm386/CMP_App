package org.practice.project.navigation.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.practice.project.navigation.Destinations

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Destinations.HOME,
        modifier = modifier
    ){
        composable (Destinations.LOGIN){
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                }
            )
        }


        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = onLoginSuccess,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
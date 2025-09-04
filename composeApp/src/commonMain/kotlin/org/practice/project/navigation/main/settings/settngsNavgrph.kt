package org.practice.project.navigation.main.settings


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.practice.project.navigation.RootGraph
import org.practice.project.navigation.ScreenContent


fun NavGraphBuilder.settingsNavGraph(navController: NavHostController){

    navigation(
        route = RootGraph.SETTINGS,
        startDestination = SettingsScreenRoutes.BtootmSheets.route
    ) {
        composable(route = SettingsScreenRoutes.BtootmSheets.route) {
            BottomSheetScreen(
                name = SettingsScreenRoutes.BtootmSheets.route,
                onClick = {},
            )
        }

    }

}


sealed class SettingsScreenRoutes(val route: String) {
    object BtootmSheets : SettingsScreenRoutes(route = "BOTTOM_SHEETS")
}
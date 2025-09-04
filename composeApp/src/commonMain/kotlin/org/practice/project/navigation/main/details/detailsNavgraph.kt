package org.practice.project.navigation.main.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.practice.project.navigation.RootGraph
import org.practice.project.navigation.ScreenContent


fun NavGraphBuilder.detailsNavGraph(navController: NavHostController){

    navigation(
        route = RootGraph.DETAILS,
        startDestination = DetailsScreenRoutes.Information.route
    ) {
        composable(route = DetailsScreenRoutes.Information.route) {
            ScreenContent(
                name = DetailsScreenRoutes.Information.route,
                onclick = {
                    navController.navigate(DetailsScreenRoutes.Overview.route)
                }
            )
        }

        composable(route = DetailsScreenRoutes.Overview.route) {
            ScreenContent(name = DetailsScreenRoutes.Overview.route,
                onclick = {
                    navController.popBackStack(
                        route = DetailsScreenRoutes.Information.route,
                        inclusive = false
                    )
                }
            )
        }
    }

}


sealed class DetailsScreenRoutes(val route: String) {
    object Information : DetailsScreenRoutes(route = "INFORMATION")
    object Overview : DetailsScreenRoutes(route = "OVERVIEW")
}
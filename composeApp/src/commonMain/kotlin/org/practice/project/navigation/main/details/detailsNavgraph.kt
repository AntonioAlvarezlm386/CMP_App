package org.practice.project.navigation.main.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.practice.project.navigation.RootGraph
import org.practice.project.navigation.ScreenContent
import org.practice.project.paging.ProductsPaging


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
            ProductsPaging(name = DetailsScreenRoutes.Overview.route,
                onClick = {
                    navController.popBackStack(
                        route = DetailsScreenRoutes.Information.route,
                        inclusive = false
                    )
                },
                toTabs = {
                    navController.navigate(DetailsScreenRoutes.Tabs.route)
                }
            )
        }


        composable(route = DetailsScreenRoutes.Tabs.route) {
            TabsScreen(
                name = DetailsScreenRoutes.Tabs.route,
                onClick = {
                    navController.popBackStack(
                        DetailsScreenRoutes.Overview.route,
                        inclusive = true
                    )
                }
            )
        }
    }

}


sealed class DetailsScreenRoutes(val route: String) {
    object Information : DetailsScreenRoutes(route = "INFORMATION")
    object Overview : DetailsScreenRoutes(route = "OVERVIEW")
    object Tabs : DetailsScreenRoutes(route = "TABS")
}
package org.practice.project.navigation.main.catalog

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.practice.project.navigation.Destinations

// CatalogNavGraph.kt
fun NavGraphBuilder.catalogNavGraph(
    navController: NavHostController,
    showBottomBar: (Boolean) -> Unit
) {
    navigation(
        startDestination = "${Destinations.CATALOG}/{${Destinations.CATALOG_TYPE}}",
        route = Destinations.CATALOG
    ) {
        composable(
            route = "${Destinations.CATALOG}/{${Destinations.CATALOG_TYPE}}",
            arguments = listOf(
                navArgument(Destinations.CATALOG_TYPE) {
                    type = NavType.StringType
                    defaultValue = "clients"
                }
            )
        ) { backStackEntry ->

            val catalogType = backStackEntry.savedStateHandle.get<String>(Destinations.CATALOG_TYPE) ?: "clients"

            CatalogScreen(
                currentCatalogType = catalogType,
                onCatalogTypeChanged = { newType ->
                    navController.navigate("${Destinations.CATALOG}/$newType") {
                        popUpTo(Destinations.CATALOG) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
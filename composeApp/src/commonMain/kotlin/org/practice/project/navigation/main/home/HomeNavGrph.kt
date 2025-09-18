package org.practice.project.navigation.main.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.practice.project.navigation.RootGraph
import org.practice.project.navigation.RootNavGraph
import org.practice.project.navigation.ScreenContent
import org.practice.project.navigation.main.details.detailsNavGraph
import org.practice.project.navigation.main.maps.MapScreen
import org.practice.project.navigation.main.profile.ProfileScreen
import org.practice.project.navigation.main.settings.settingsNavGraph


@Composable
fun HomeNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route = RootGraph.HOME,
        startDestination = BottomBarScreenItems.Home.route
    ){
        composable(BottomBarScreenItems.Home.route){
            ScreenContent(
                name = BottomBarScreenItems.Home.route,
                onclick = {
                    navController.navigate(RootGraph.DETAILS)
                }
            )
        }


        composable(BottomBarScreenItems.Settings.route) {
            ScreenContent(
                name = BottomBarScreenItems.Settings.route,
                onclick = {
                    navController.navigate(RootGraph.SETTINGS)
                }
            )
        }


        composable(BottomBarScreenItems.Profile.route) {
            ProfileScreen(
                name = BottomBarScreenItems.Profile.route,
                onMapClick = { navController.navigate(RootGraph.MAP) }
            )
        }

        composable(route = RootGraph.MAP) {
            MapScreen()
        }


        detailsNavGraph(navController)

        settingsNavGraph(navController)

    }
}
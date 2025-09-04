package org.practice.project.navigation.main.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.practice.project.navigation.RootGraph
import org.practice.project.navigation.ScreenContent
import org.practice.project.navigation.main.details.detailsNavGraph


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
                }
            )
        }


        composable(BottomBarScreenItems.Profile.route) {
            ScreenContent(
                name = BottomBarScreenItems.Profile.route,
                onclick = {}
            )
        }

        detailsNavGraph(navController)
    }
}
package org.practice.project.navigation.main.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.practice.project.camera.CameraScreen
import org.practice.project.camera.CameraScreenViewModel
import org.practice.project.firma.ui.SignatureScreen
import org.practice.project.navigation.RootGraph
import org.practice.project.navigation.RootNavGraph
import org.practice.project.navigation.ScreenContent
import org.practice.project.navigation.main.details.detailsNavGraph
import org.practice.project.navigation.main.maps.MapScreen
import org.practice.project.navigation.main.profile.ProfileScreen
import org.practice.project.navigation.main.settings.settingsNavGraph


@OptIn(ExperimentalMaterial3Api::class)
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
                onMapClick = { navController.navigate(RootGraph.MAP) },
                onSignatureClick = { navController.navigate(RootGraph.SIGNATURE) },
                onCameraClick = { navController.navigate(RootGraph.CAMERA) }
            )
        }

        composable(RootGraph.SIGNATURE) {
            SignatureScreen (
                name = RootGraph.SIGNATURE
            )
        }



        composable(route = RootGraph.MAP) {
            MapScreen()
        }

        composable(route = RootGraph.CAMERA) {
            CameraScreen(
            )
        }



        detailsNavGraph(navController)

        settingsNavGraph(navController)

    }
}
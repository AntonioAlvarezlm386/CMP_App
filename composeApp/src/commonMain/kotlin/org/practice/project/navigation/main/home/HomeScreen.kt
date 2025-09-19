package org.practice.project.navigation.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.practice.project.navigation.Event
import org.practice.project.navigation.EventBus
import org.practice.project.navigation.main.CustomSnackbar

// HomeScreen.kt
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    rootNavController: NavHostController = rememberNavController()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val lifecicleowner = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(lifecicleowner){
        lifecicleowner.repeatOnLifecycle(Lifecycle.State.STARTED){
            EventBus.events.collect { event ->
                when (event){
                    Event.NavigateToHomeScreen -> TODO()
                    is Event.Toast -> {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = event.message,
                                actionLabel = "Accion",
                                duration = SnackbarDuration.Short
                            )
                            when (result){
                                SnackbarResult.Dismissed -> {
                                    snackbarHostState
                                }
                                SnackbarResult.ActionPerformed -> {

                                }
                            }
                        }
                    }
                }
            }
        }
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){
                CustomSnackbar()
            }
        },
        bottomBar = {
            BottomBar(
                navController = rootNavController
            )
        }
    ) {
        HomeNavGraph(rootNavController)
    }

}


@Composable
fun BottomBar(
    navController: NavHostController
){
    val screens = listOf(
        BottomBarScreenItems.Home,
        BottomBarScreenItems.Profile,
        BottomBarScreenItems.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any {it.route == currentDestination?.route}
    if(bottomBarDestination){
            NavigationBar {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreenItems,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem (
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}
package org.practice.project.buttomBar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

class BottomBarScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(
            HomeTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(HomeTab, FavTab, ProfileTab)
                )
            }
        ){it ->
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = it.current.options.title)
                        }
                    )
                },
                bottomBar = {
                    // de material
                    BottomNavigation {
                        val tabNavigator : TabNavigator = LocalTabNavigator.current

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == HomeTab.key,
                            label = {
                                Text(text = HomeTab.options.title)
                            },
                            icon = {
                                Icon(painter = HomeTab.options.icon!!, contentDescription = null)
                            },
                            onClick = {
                                tabNavigator.current = HomeTab
                            }
                        )


                        BottomNavigationItem(
                            selected = tabNavigator.current.key == FavTab.key,
                            label = {
                                Text(text = FavTab.options.title)
                            },
                            icon = {
                                Icon(painter = FavTab.options.icon!!, contentDescription = null)
                            },
                            onClick = {
                                tabNavigator.current = FavTab
                            }
                        )

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == ProfileTab.key,
                            label = {
                                Text(text = ProfileTab.options.title)
                            },
                            icon = {
                                Icon(painter = ProfileTab.options.icon!!, contentDescription = null)
                            },
                            onClick = {
                                tabNavigator.current = ProfileTab
                            }
                        )
                    }
                },
                content = {
                    CurrentTab()
                }
            )
        }
    }
}
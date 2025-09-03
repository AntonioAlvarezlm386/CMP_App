package org.practice.project.navigation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import org.practice.project.navigation.Destinations


// BottomBar.kt
@Composable
fun BottomBar(
    currentDestination: NavDestination?,
    onNavigate: (String) -> Unit
) {
    val destinations = listOf(
        Destinations.HOME to "Inicio",
        Destinations.CATALOG to "Catálogos",
        Destinations.AGENDA to "Agenda"
    )

    NavigationBar {
        destinations.forEach { (destination, label) ->
            val selected = currentDestination?.route == destination

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(destination) },
                icon = {
                    Icon(
                        imageVector = when (destination) {
                            Destinations.HOME -> Icons.Default.Home
                            Destinations.CATALOG -> Icons.Default.AccountBox
                            Destinations.AGENDA -> Icons.Default.Menu
                            else -> Icons.Default.Home
                        },
                        contentDescription = label
                    )
                },
                label = { Text(label) }
            )
        }
    }
}
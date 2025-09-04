package org.practice.project.navigation.main.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreenItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreenItems(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreenItems(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Default.Person
    )

    object Settings : BottomBarScreenItems(
        route = "SETTINGS",
        title = "SETTINGS",
        icon = Icons.Default.Settings
    )
}
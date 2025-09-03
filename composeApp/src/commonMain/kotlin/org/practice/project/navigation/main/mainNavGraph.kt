package org.practice.project.navigation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.practice.project.navigation.Destinations
import org.practice.project.navigation.main.agenda.agendaNavGraph
import org.practice.project.navigation.main.catalog.catalogNavGraph
import org.practice.project.navigation.main.home.HomeScreen

// MainNavGraph.kt
// MainNavGraph.kt - COMO FUNCIÓN DE EXTENSIÓN
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    showBottomBar: (Boolean) -> Unit
) {
    // Home Screen
    composable(Destinations.HOME) {
        HomeScreen(
            onNavigateToAgendaDetail = { agendaId ->
                navController.navigate("${Destinations.AGENDA_DETAIL}/$agendaId")
            }
        )
    }
    // ✅ CORRECTO: Ruta diferente para el grafo de catalog
    navigation(
        startDestination = "catalog_main/clients",  // ← Ruta diferente
        route = "catalog_main"  // ← NO usa Destinations.CATALOG
    ) {
        catalogNavGraph(navController, showBottomBar)
    }

    // ✅ CORRECTO: Ruta diferente para el grafo de agenda
    navigation(
        startDestination = Destinations.AGENDA,
        route = "agenda_main"  // ← Ruta única para el grafo
    ) {
        agendaNavGraph(navController, showBottomBar)
    }
}


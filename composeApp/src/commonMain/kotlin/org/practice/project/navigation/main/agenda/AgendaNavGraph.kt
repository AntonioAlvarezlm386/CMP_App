package org.practice.project.navigation.main.agenda

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.practice.project.navigation.Destinations




// AgendaNavGraph.kt - VERSIÓN CORREGIDA
fun NavGraphBuilder.agendaNavGraph(
    navController: NavHostController,
    showBottomBar: (Boolean) -> Unit
) {
    // ✅ Ruta del grafo DIFERENTE de la pantalla
    navigation(
        startDestination = Destinations.AGENDA_SCREEN,  // ← Pantalla inicial
        route = Destinations.AGENDA_GRAPH               // ← Ruta única del grafo
    ) {
        composable(Destinations.AGENDA_SCREEN) {
            AgendaScreen(
                onNavigateToDetail = { agendaId ->
                    navController.navigate("${Destinations.AGENDA_DETAIL}/$agendaId")
                }
            )
        }

        composable(
            route = "${Destinations.AGENDA_DETAIL}/{${Destinations.AGENDA_ID}}",
            arguments = listOf(
                navArgument(Destinations.AGENDA_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val agendaId = backStackEntry.savedStateHandle.get<String>(Destinations.AGENDA_ID) ?: ""

            LaunchedEffect(Unit) { showBottomBar(false) }
            DisposableEffect(Unit) {
                onDispose { showBottomBar(true) }
            }

            AgendaDetailScreen(
                agendaId = agendaId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

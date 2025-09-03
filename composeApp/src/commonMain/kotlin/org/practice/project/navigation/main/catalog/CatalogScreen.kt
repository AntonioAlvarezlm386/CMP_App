package org.practice.project.navigation.main.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// CatalogScreen.kt
@Composable
fun CatalogScreen(
    currentCatalogType: String,
    onCatalogTypeChanged: (String) -> Unit
) {
    val catalogTypes = listOf("clients", "articles", "prices")

    Column(modifier = Modifier.fillMaxSize()) {
        // Chips de navegación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            catalogTypes.forEach { type ->
                FilterChip(
                    selected = currentCatalogType == type,
                    onClick = { onCatalogTypeChanged(type) },
                    label = { Text(type.capitalize()) }
                )
            }
        }

        // Contenido según el tipo
        when (currentCatalogType) {
            "clients" -> ClientsScreen()
            "articles" -> ArticlesScreen()
            "prices" -> PricesScreen()
        }
    }
}
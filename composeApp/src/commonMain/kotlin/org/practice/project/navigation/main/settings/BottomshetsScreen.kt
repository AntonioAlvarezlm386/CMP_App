package org.practice.project.navigation.main.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun BottomSheetScreen(
    name: String,
    onClick: () -> Unit
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp)){
                Text(
                    modifier = Modifier.clickable{ onClick() },
                    text = name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    ) { contentPadding ->

        DraggableBottomSheet()
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraggableBottomSheet() {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false // Permite posiciones intermedias
    )
    val scope = rememberCoroutineScope()
    val isVisible = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { isVisible.value = true }) {
            Text(text = "Abrir BottomSheet")
        }
    }

    if (isVisible.value) {
        ModalBottomSheet(
            onDismissRequest = { isVisible.value = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Handle para arrastrar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(Color.Gray.copy(alpha = 0.5f))
                        .clip(RoundedCornerShape(2.dp))
                        .align(Alignment.CenterHorizontally)
                )

                // Contenido expandible
                var isExpanded by remember { mutableStateOf(false) }

                Text(
                    "Contenido Dinámico",
                    style = MaterialTheme.typography.headlineSmall
                )

                if (isExpanded) {
                    Text(
                        "Este es contenido adicional que se muestra cuando se expande. " +
                                "Puede ser tan largo como necesites y el BottomSheet se ajustará automáticamente.",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    LazyColumn {
                        items(10) { index ->
                            Text(
                                "Item $index",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }

                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(if (isExpanded) "Contraer" else "Expandir")
                }

                Button(
                    onClick = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isVisible.value = false }
                    }
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}
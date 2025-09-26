package org.practice.project.firma.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.practice.project.navigation.Event

@Composable
fun SignatureScreen(
    name: String
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        var path by remember { mutableStateOf(Path()) }
        val viewmodel  = viewModel<DrawingViewModel>()
        val state by viewmodel.state.collectAsStateWithLifecycle()

        Column(
           modifier = Modifier.fillMaxSize().padding(all = 12.dp)
            
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )

            SignaturePad(
                modifier = Modifier.fillMaxWidth().weight(1f),
                paths = state.paths,
                onAction = viewmodel::onEvent,
                currentPath = state.currentPath
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                allColors.fastForEach { color ->
                    Box(
                        modifier = Modifier.size(50.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 2.dp,
                                color = if(state.selectedColor == color){
                                    Color.White
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable{
                                viewmodel.onEvent(DrawincAtions.SelectColor(color))
                            }
                    )
                }
            }
        }
    }
}
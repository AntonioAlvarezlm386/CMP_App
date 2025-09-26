package org.practice.project.firma.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? =  null,
    val paths: List<PathData> = emptyList<PathData>()
)

data class PathData(
    val id:String,
    val color: Color,
    val Path: List<Offset>
)
val allColors = listOf<Color>(
    Color.Black,
    Color.Red,
    Color.Blue
)


sealed interface DrawincAtions{
    data object OnNewPathStart: DrawincAtions
    data class OnDraw(val offset: Offset): DrawincAtions
    data object OnPathEnd: DrawincAtions
    data class SelectColor(val color: Color): DrawincAtions
    data object onClecarCanvas: DrawincAtions
}

class DrawingViewModel(

): ViewModel() {
    private val _state = MutableStateFlow(DrawingState())
    val state = _state.asStateFlow()

    @OptIn(ExperimentalTime::class)
    fun onEvent(event: DrawincAtions){
        when (event){
            is DrawincAtions.OnDraw -> {
                val currentPathData = _state.value.currentPath ?: return
                _state.update {
                    it.copy(
                        currentPath = currentPathData.copy(
                            Path = currentPathData.Path + event.offset
                        )
                    )
                }
            }
            DrawincAtions.OnNewPathStart -> {
                _state.update { it.copy(
                    currentPath = PathData(
                        id = Clock.System.now().toEpochMilliseconds().toString(),
                        color = it.selectedColor,
                        Path = emptyList()
                    )
                ) }
            }
            DrawincAtions.OnPathEnd -> {
                val currentPathData = _state.value.currentPath ?: return
                _state.update { it.copy(
                    currentPath = null,
                    paths = it.paths + currentPathData
                ) }
            }
            is DrawincAtions.SelectColor -> {
                _state.update { it.copy(
                    selectedColor = event.color
                ) }
            }
            DrawincAtions.onClecarCanvas -> {
                _state.update { it.copy(
                    paths = emptyList(),
                    currentPath = null
                ) }
            }
        }
    }
}
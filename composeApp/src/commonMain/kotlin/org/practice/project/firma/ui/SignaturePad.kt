package org.practice.project.firma.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun SignaturePad(
    modifier: Modifier = Modifier,
    paths: List<PathData>,
    onAction: (DrawincAtions) -> Unit,
    currentPath: PathData?
) {

    Box(
        modifier = modifier
            .background(Color.White)
            .border(1.dp, Color.Black)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { onAction(DrawincAtions.OnNewPathStart)},
                    onDragEnd = { onAction(DrawincAtions.OnPathEnd) },
                    onDrag = { change, dragAmount ->
                        onAction(DrawincAtions.OnDraw(change.position))
                    },
                    onDragCancel = {onAction(DrawincAtions.OnPathEnd)}
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            paths.forEach { pathData ->
                drawPath(
                    path = pathData.Path,
                    color = pathData.color
                )
            }
            currentPath?.let {
                drawPath(
                    path = it.Path,
                    color = it.color
                )
            }
        }

        // Botón para "guardar" la firma en bitmap
        Button(
            onClick = {
                onAction(DrawincAtions.onClecarCanvas)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Guardar firma")
        }
    }
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    thickness: Float = 10f
){
    val smoothedPath = Path().apply {
        if(path.isNotEmpty()){
            moveTo(path.first().x, path.first().y )

            val smoothness = 5
            for(i in 1..path.lastIndex){
                val from = path[i - 1]
                val to = path[i]

                val dx = abs(from.x -to.y)
                val dy = abs(from.y - to.x)
                if(dx >= smoothness || dy >= smoothness){
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path =  smoothedPath,
        color =  color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}


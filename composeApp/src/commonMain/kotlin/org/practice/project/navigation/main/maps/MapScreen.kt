package org.practice.project.navigation.main.maps



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.dellisd.spatialk.geojson.Position
import io.github.dellisd.spatialk.geojson.dsl.PointDsl
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.layers.LineLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.GeoJsonOptions
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.sources.rememberImageSource
import org.maplibre.compose.style.BaseStyle



@Composable
fun MapScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Map Screen",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val camera =
                rememberCameraState(
                    firstPosition =
                        CameraPosition(
                            target = Position(latitude = 19.4326, longitude = -100.1332),
                            zoom = 13.0
                        )
                )







            MaplibreMap(
                modifier = Modifier
                    .size(300.dp, 300.dp) // Ancho y alto fijos
                    .clip(RoundedCornerShape(12.dp)), // Bordes redondeados opcionales
                baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
                cameraState = camera
            ){
                val amtrakRoutes =
                rememberGeoJsonSource(
                    data = GeoJsonData.JsonString(
                        """
            {
              "type": "FeatureCollection",
              "features": [
                {
                  "type": "Feature",
                  "geometry": {
                    "type": "Point",
                    "coordinates": [-100.1332, 19.4326]
                  },
                  "properties": {
                    "name": "CDMX marker"
                  }
                }
              ]
            }
            """.trimIndent()
                    ),
                    options = GeoJsonOptions(tolerance = 0.1f),
                )
                CircleLayer(
                    id = "amtrak-routes-casing",
                    source = amtrakRoutes
                )
            }
        }
    }
}



package org.practice.project.buttomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object FavTab : Tab {
    override val options : TabOptions
        @Composable
        get() {
            val icon : VectorPainter = rememberVectorPainter(Icons.Default.Favorite)

            return remember {
                TabOptions(
                    index = 1u,
                    title = "Fav",
                    icon = icon
                )
            }
        }


    @Composable
    override fun Content(){
        Box(
            modifier = Modifier.fillMaxSize().background(color = Color.Yellow),
            contentAlignment = Alignment.Center
        ){
            Text(text = "FavScreen", color = Color.Black)
        }
    }


}
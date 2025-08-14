package org.practice.project



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

import cmp_app.composeapp.generated.resources.Res
import cmp_app.composeapp.generated.resources.compose_multiplatform
import org.practice.project.buttomBar.BottomBarScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = MainScreen())
    }
}

class MainScreen: Screen{
    @Composable
    override fun Content(){
        val navigator : Navigator? = LocalNavigator.current

        var showContent by remember { mutableStateOf(value = false) }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { navigator?.push(item =  SecondScreen(userName = "Jhon Doe")) }) {
                Text(text = "Navegacion Basica")
            }
            Spacer(Modifier.height(height = 18.dp))
            Button(onClick = { navigator?.push(item = BottomBarScreen()) }) {
                Text(text = "Bootom Bar")
            }
        }
    }
}

data class SecondScreen(val userName : String) : Screen{
    @Composable
    override fun Content() {
        val navigator : Navigator? = LocalNavigator.current

        Column(
            modifier = Modifier.fillMaxSize().background(Color.LightGray),
            verticalArrangement = Arrangement.Center
        ){
            Text(text = "Segunda Pantalla: Mostrando datos de ${userName}")

            Button(onClick = { navigator?.pop() }) {
                Text("Volver")
            }
        }
    }

}
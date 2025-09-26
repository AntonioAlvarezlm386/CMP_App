package org.practice.project.navigation.main.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.coroutineScope
import org.practice.project.navigation.Event
import org.practice.project.navigation.EventBus
import org.practice.project.navigation.RootGraph

@Composable
fun ProfileScreen(
    name: String,
    onMapClick:() -> Unit,
    onSignatureClick:() -> Unit
){
    val viewmodel = ProfileViewmodel()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

    Column {
        Text(
            modifier = Modifier.clickable{ onMapClick() },
            text = name,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier.clickable{
                viewmodel.onDateTimeClick(Event.Toast("Mensaje dese click"))
            },
            text = "DATETIME",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier.clickable{ onSignatureClick() },
            text = RootGraph.SIGNATURE,
            style = MaterialTheme.typography.titleLarge
        )
    }
    }
}
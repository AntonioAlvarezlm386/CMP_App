package org.practice.project.navigation.main.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(name: String, onMapClick:() -> Unit){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier.clickable{ onMapClick() },
            text = name,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
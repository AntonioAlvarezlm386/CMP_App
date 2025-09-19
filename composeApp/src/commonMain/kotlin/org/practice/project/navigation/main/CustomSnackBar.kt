package org.practice.project.navigation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar() {
    Snackbar(
        modifier = Modifier.padding(all = 10.dp),
        containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
        shape = RoundedCornerShape(25)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                Text("Titulo")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Tienes una nueva notificación")
            }
            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                Button(onClick = { /*TODO*/ }) {
                    Text("Acción")
                }
            }


        }
    }
}
package org.practice.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test.Icontesttest
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var permissionIsGranted by remember { mutableStateOf(false) }
        var isDialogVisible by remember { mutableStateOf(false) }


        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        BindEffect(controller)

        LaunchedEffect(Unit){
            permissionIsGranted = controller.isPermissionGranted(permission = Permission.CAMERA)
        }


        if(isDialogVisible){
            AlertDialog(
                onDismissRequest = {
                    isDialogVisible = false
                },
                title = {
                    Text(text = "Camera Permisson")
                },
                confirmButton = {
                    Button(
                        onClick = {controller.openAppSettings()}
                    ){
                        Text("Abrir ajustes")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {isDialogVisible = false}
                    ){
                        Text("Cerarr")
                    }
                }
            )
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {

            Text(
                if(permissionIsGranted) "permission grnated" else "permission denied"
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        runCatching {

                            controller.providePermission(Permission.CAMERA)
                            permissionIsGranted = true

                        }.onFailure { ex ->
                            when(ex){
                                is DeniedAlwaysException->{
                                    isDialogVisible = true
                                }
                            }

                        }
                    }
                }
            ){
                Text("Camera Perission")
            }

        }
    }
}


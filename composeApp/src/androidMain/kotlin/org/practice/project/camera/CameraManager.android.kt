package org.practice.project.camera

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    var tmpPhotoUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }

    val cameraLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && tmpPhotoUri != null) {
                try {
                    val bitmap = tmpPhotoUri!!.toBitMap(contentResolver)
                    if (bitmap != null) {
                        println("Bitmap created successfully")
                        onResult.invoke(SharedImage(bitmap))
                    } else {
                        println("Failed to create bitmap from URI")
                        onResult.invoke(null)
                    }
                } catch (e: Exception) {
                    onResult.invoke(null)
                }
            } else {
                onResult.invoke(null)
            }
        }
    )

    return remember {
        CameraManager(
            onLaunch = { // eset es el calllback que se le pasa por propiedad, y es la ue se ejecuta en el metodo de la clase
                try {
                    val uri = AndroidFileProvider.createTempFileUri(
                        context = context,
                        prefix = "image",
                        extensions = "png"
                    )
                    if(uri != null){
                        tmpPhotoUri = uri
                        cameraLaunch.launch(input = uri)
                    } else {
                        onResult.invoke(null)
                    }
                } catch (e: Exception) {
                    onResult.invoke(null)
                }
            }
        )
    }
}
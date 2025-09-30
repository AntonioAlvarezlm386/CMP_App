package org.practice.project.camera

import androidx.compose.runtime.Composable

actual class CameraManager actual constructor(onLaunch: () -> Unit) {
    actual fun launch() {
    }
}

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    TODO("Not yet implemented")
}
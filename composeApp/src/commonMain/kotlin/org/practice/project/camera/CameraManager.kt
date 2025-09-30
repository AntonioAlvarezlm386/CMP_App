package org.practice.project.camera

import androidx.compose.runtime.Composable


/***
 * Para menajr la parte composable de este proceso
 */
@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager

expect class CameraManager(onLaunch: () -> Unit) {
    fun launch()
}
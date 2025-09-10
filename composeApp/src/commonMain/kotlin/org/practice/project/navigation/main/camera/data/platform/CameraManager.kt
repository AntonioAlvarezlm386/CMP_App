package org.practice.project.navigation.main.camera.data.platform

expect class CameraManager(context: Any? = null) {
    suspend fun capturePhoto(): Result<ByteArray>
    fun hasCamera(): Boolean
    fun requestCameraPermission(): Boolean
}
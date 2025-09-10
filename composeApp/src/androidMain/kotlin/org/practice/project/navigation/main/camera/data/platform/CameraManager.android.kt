package org.practice.project.navigation.main.camera.data.platform

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayOutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume

actual class CameraManager actual constructor(
    context: Any?
) {
    private val context = context as Context
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private var imageCapture: ImageCapture? = null

    actual suspend fun capturePhoto(): Result<ByteArray> = suspendCancellableCoroutine { continuation ->
        val imageCapture = imageCapture ?: run {
            continuation.resume(Result.failure(Exception("Cámara no inicializada")))
            return@suspendCancellableCoroutine
        }

        val outputStream = ByteArrayOutputStream()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(outputStream).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val bytes = outputStream.toByteArray()
                    continuation.resume(Result.success(bytes))
                }

                override fun onError(exception: ImageCaptureException) {
                    continuation.resume(Result.failure(exception))
                }
            }
        )
    }

    actual fun hasCamera(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    actual fun requestCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    suspend fun initializeCamera(lifecycleOwner: LifecycleOwner): Result<Unit> {
        return try {
            val cameraProvider = ProcessCameraProvider.getInstance(context).get()

            // Preview
            val preview = Preview.Builder().build()

            // ImageCapture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            // Seleccionar cámara trasera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Desligar casos de uso antes de religar
                cameraProvider.unbindAll()

                // Religar casos de uso a la cámara
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                Result.success(Unit)
            } catch (exc: Exception) {
                Result.failure(exc)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun shutdown() {
        cameraExecutor.shutdown()
    }
}

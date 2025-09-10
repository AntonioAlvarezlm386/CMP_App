package org.practice.project.navigation.main.camera.data.platform



import kotlinx.cinterop.*
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AVFoundation.*
import platform.CoreGraphics.CGRectMake
import platform.Foundation.*
import platform.UIKit.*
import kotlin.coroutines.resume

actual class CameraManager {
    private var captureSession: AVCaptureSession? = null
    private var photoOutput: AVCapturePhotoOutput? = null

    actual suspend fun capturePhoto(): Result<ByteArray> = suspendCancellableCoroutine { continuation ->
        val photoOutput = this.photoOutput
        if (photoOutput == null) {
            continuation.resume(Result.failure(Exception("Cámara no inicializada")))
            return@suspendCancellableCoroutine
        }

        val settings = AVCapturePhotoSettings()
        settings.flashMode = AVCaptureFlashModeOff

        val delegate: PhotoCaptureDelegate = PhotoCaptureDelegate { result ->
            continuation.resume(result)
        }

        photoOutput.capturePhotoWithSettings(settings, delegate = delegate)
    }

    actual fun hasCamera(): Boolean {
        val deviceDiscoverySession = AVCaptureDeviceDiscoverySession.discoverySessionWithDeviceTypes(
            listOf(AVCaptureDeviceTypeBuiltInWideAngleCamera),
            AVMediaTypeVideo,
            AVCaptureDevicePositionBack
        )
        return deviceDiscoverySession.devices.isNotEmpty()
    }

    actual fun requestCameraPermission(): Boolean {
        return when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
            AVAuthorizationStatusAuthorized -> true
            else -> false
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    suspend fun initializeCamera(): Result<Unit> = suspendCancellableCoroutine { continuation ->
        val session = AVCaptureSession()
        session.sessionPreset = AVCaptureSessionPresetPhoto

        // Configurar dispositivo de cámara
        val deviceDiscoverySession = AVCaptureDeviceDiscoverySession.discoverySessionWithDeviceTypes(
            listOf(AVCaptureDeviceTypeBuiltInWideAngleCamera),
            AVMediaTypeVideo,
            AVCaptureDevicePositionBack
        )

        val videoDevice = deviceDiscoverySession.devices.firstOrNull() as? AVCaptureDevice
        if (videoDevice == null) {
            continuation.resume(Result.failure(Exception("No se encontró cámara")))
            return@suspendCancellableCoroutine
        }

        val videoInput = try {
            AVCaptureDeviceInput.deviceInputWithDevice(videoDevice, null)
        } catch (e: Exception) {
            continuation.resume(Result.failure(e))
            return@suspendCancellableCoroutine
        }

        if (session.canAddInput(videoInput)) {
            session.addInput(videoInput)
        } else {
            continuation.resume(Result.failure(Exception("No se pudo agregar entrada de video")))
            return@suspendCancellableCoroutine
        }

        // Configurar salida de foto
        val photoOutput = AVCapturePhotoOutput()
        if (session.canAddOutput(photoOutput)) {
            session.addOutput(photoOutput)
            this.photoOutput = photoOutput
        } else {
            continuation.resume(Result.failure(Exception("No se pudo agregar salida de foto")))
            return@suspendCancellableCoroutine
        }

        this.captureSession = session
        session.startRunning()

        continuation.resume(Result.success(Unit))
    }

    fun stopCamera() {
        captureSession?.stopRunning()
        captureSession = null
        photoOutput = null
    }
}

class PhotoCaptureDelegate(
    private val completion: (Result<ByteArray>) -> Unit
) : NSObject(), AVCapturePhotoCaptureDelegate {

    override fun captureOutput(
        output: AVCapturePhotoOutput,
        didFinishProcessingPhoto: AVCapturePhoto,
        error: NSError?
    ) {
        if (error != null) {
            completion(Result.failure(Exception(error.localizedDescription)))
            return
        }

        val imageData = didFinishProcessingPhoto.fileDataRepresentation()
        if (imageData != null) {
            val bytes = imageData.bytes?.readBytes(imageData.length.toInt())
            if (bytes != null) {
                completion(Result.success(bytes))
            } else {
                completion(Result.failure(Exception("Error al leer datos de imagen")))
            }
        } else {
            completion(Result.failure(Exception("No se pudieron obtener datos de imagen")))
        }
    }
}

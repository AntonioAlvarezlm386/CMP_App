package org.practice.project.navigation.main.camera.data.repository

import org.practice.project.navigation.main.camera.data.platform.CameraManager
import org.practice.project.navigation.main.camera.data.platform.FileManager
import org.practice.project.navigation.main.camera.domain.model.PhotoData
import org.practice.project.navigation.main.camera.domain.repository.CameraRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class CameraRepositoryImpl(
    private val cameraManager: CameraManager,
    private val fileManager: FileManager
) : CameraRepository {

    @OptIn(ExperimentalTime::class)
    override suspend fun takePhoto(): Result<PhotoData> {
        return try {
            if (!cameraManager.hasCamera()) {
                return Result.failure(Exception("Cámara no disponible"))
            }

            // Capturar foto
            val photoBytes = cameraManager.capturePhoto().getOrThrow()

            // Generar nombre único
            val timestamp = Clock.System.now().toEpochMilliseconds()
            val fileName = "photo_$timestamp.jpg"

            // Guardar foto
            val filePath = fileManager.savePhotoToGallery(photoBytes, fileName).getOrThrow()

            // Crear PhotoData
            val photoData = PhotoData(
                id = timestamp.toString(),
                fileName = fileName,
                filePath = filePath,
                timestamp = timestamp,
                size = photoBytes.size.toLong()
            )

            Result.success(photoData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllPhotos(): Result<List<PhotoData>> {
        return try {
            val photoPaths = fileManager.getAllPhotoFiles().getOrThrow()
            val photos = photoPaths.mapNotNull { path ->
                try {
                    val fileName = path.substringAfterLast("/")
                    val timestamp = fileName.substringAfter("photo_").substringBefore(".jpg").toLongOrNull()

                    if (timestamp != null) {
                        // Obtener tamaño del archivo
                        val bytes = fileManager.getPhotoFromPath(path).getOrNull()

                        PhotoData(
                            id = timestamp.toString(),
                            fileName = fileName,
                            filePath = path,
                            timestamp = timestamp,
                            size = bytes?.size?.toLong() ?: 0L
                        )
                    } else null
                } catch (e: Exception) {
                    null
                }
            }.sortedByDescending { it.timestamp }

            Result.success(photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePhoto(photoId: String): Result<Unit> {
        return try {
            val photos = getAllPhotos().getOrThrow()
            val photo = photos.find { it.id == photoId }
                ?: return Result.failure(Exception("Foto no encontrada"))

            fileManager.deletePhotoFromPath(photo.filePath).getOrThrow()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPhotoBytes(photoData: PhotoData): Result<ByteArray> {
        return fileManager.getPhotoFromPath(photoData.filePath)
    }
}

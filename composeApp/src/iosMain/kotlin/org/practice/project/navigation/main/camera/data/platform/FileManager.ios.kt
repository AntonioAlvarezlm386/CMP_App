package org.practice.project.navigation.main.camera.data.platform

import kotlinx.cinterop.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.*
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

actual class FileManager {

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun savePhotoToGallery(bytes: ByteArray, fileName: String): Result<String> {
        return withContext(Dispatchers.Default) {
            try {
                val documentsPath = NSSearchPathForDirectoriesInDomains(
                    NSDocumentDirectory,
                    NSUserDomainMask,
                    true
                ).firstOrNull() as? String ?: throw Exception("No se pudo obtener directorio de documentos")

                val photosDir = "$documentsPath/MyAppPhotos"
                val fileManager = NSFileManager.defaultManager

                // Crear directorio si no existe
                if (!fileManager.fileExistsAtPath(photosDir)) {
                    fileManager.createDirectoryAtPath(
                        photosDir,
                        true,
                        null,
                        null
                    )
                }

                val filePath = "$photosDir/$fileName"
                val data = bytes.usePinned { pinned ->
                    NSData.create(
                        bytes = pinned.addressOf(0),
                        length = bytes.size.toULong()
                    )
                }

                val success = data.writeToFile(filePath, true)
                if (success) {
                    Result.success(filePath)
                } else {
                    Result.failure(Exception("Error al escribir archivo"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getPhotoFromPath(path: String): Result<ByteArray> {
        return withContext(Dispatchers.Default) {
            try {
                val fileManager = NSFileManager.defaultManager
                if (!fileManager.fileExistsAtPath(path)) {
                    return@withContext Result.failure(Exception("Archivo no existe"))
                }

                val data = NSData.dataWithContentsOfFile(path)
                    ?: return@withContext Result.failure(Exception("Error al leer archivo"))

                val bytes = ByteArray(data.length.toInt())
                data.getBytes(bytes.refTo(0).getPointer(MemScope()), data.length)

                Result.success(bytes)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun deletePhotoFromPath(path: String): Result<Unit> {
        return withContext(Dispatchers.Default) {
            try {
                val fileManager = NSFileManager.defaultManager
                if (fileManager.fileExistsAtPath(path)) {
                    fileManager.removeItemAtPath(path, null)
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getAllPhotoFiles(): Result<List<String>> {
        return withContext(Dispatchers.Default) {
            try {
                val documentsPath = NSSearchPathForDirectoriesInDomains(
                    NSDocumentDirectory,
                    NSUserDomainMask,
                    true
                ).firstOrNull() as? String ?: throw Exception("No se pudo obtener directorio de documentos")

                val photosDir = "$documentsPath/MyAppPhotos"
                val fileManager = NSFileManager.defaultManager

                if (!fileManager.fileExistsAtPath(photosDir)) {
                    return@withContext Result.success(emptyList())
                }

                val contents = fileManager.contentsOfDirectoryAtPath(photosDir, null)
                    ?: return@withContext Result.success(emptyList())

                val photoFiles = contents.mapNotNull { fileName ->
                    val name = fileName as? String
                    if (name?.lowercase()?.endsWith(".jpg") == true ||
                        name?.lowercase()?.endsWith(".jpeg") == true) {
                        "$photosDir/$name"
                    } else null
                }

                Result.success(photoFiles)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    actual fun getPhotosDirectory(): String {
        val documentsPath = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).firstOrNull() as? String ?: ""

        return "$documentsPath/MyAppPhotos"
    }
}
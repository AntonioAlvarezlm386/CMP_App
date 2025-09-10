package org.practice.project.navigation.main.camera.data.platform

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

actual class FileManager actual constructor(
    context: Any?
) {

    private val context = context as Context
    actual suspend fun savePhotoToGallery(bytes: ByteArray, fileName: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val photosDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyAppPhotos")
                if (!photosDir.exists()) {
                    photosDir.mkdirs()
                }

                val file = File(photosDir, fileName)
                FileOutputStream(file).use { output ->
                    output.write(bytes)
                }

                Result.success(file.absolutePath)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    actual suspend fun getPhotoFromPath(path: String): Result<ByteArray> {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(path)
                if (!file.exists()) {
                    return@withContext Result.failure(Exception("Archivo no existe"))
                }

                val bytes = file.readBytes()
                Result.success(bytes)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    actual suspend fun deletePhotoFromPath(path: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(path)
                if (file.exists()) {
                    file.delete()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    actual suspend fun getAllPhotoFiles(): Result<List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val photosDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyAppPhotos")
                if (!photosDir.exists()) {
                    return@withContext Result.success(emptyList())
                }

                val files = photosDir.listFiles { _, name ->
                    name.lowercase().endsWith(".jpg") || name.lowercase().endsWith(".jpeg")
                }?.map { it.absolutePath } ?: emptyList()

                Result.success(files)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    actual fun getPhotosDirectory(): String {
        val photosDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyAppPhotos")
        return photosDir.absolutePath
    }
}

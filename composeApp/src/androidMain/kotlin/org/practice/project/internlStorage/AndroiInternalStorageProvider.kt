package org.practice.project.internlStorage

import android.content.Context
import org.practice.project.InternalStoragePractice.domain.InterNalStorageProvider
import java.io.File
import java.io.FileOutputStream

class AndroiInternalStorageProvider(
    private val context: Context
): InterNalStorageProvider {
    override suspend fun saveImage(bytes: ByteArray, name: String): String? {
        return try {
            // Reutilizamos tu lógica, pero ahora trabajamos con ByteArray
            val directory = File(context.filesDir, "photos")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            // Usamos el nombre de archivo que se nos pasa, asegurando una extensión
            val fileExtension = if (name.endsWith(".jpg", ignoreCase = true)) "" else ".jpg"
            val imageFile = File(directory, name + fileExtension)

            // Guardar los bytes directamente en el archivo
            FileOutputStream(imageFile).use { outputStream ->
                outputStream.write(bytes)
            }

            println("Image saved internally at: ${imageFile.absolutePath}")
            imageFile.absolutePath

        } catch (e: Exception) {
            println("Error saving image: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}
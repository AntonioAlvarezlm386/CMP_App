package org.practice.project.camera

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

fun saveImageOnInternalStorage(context: Context, bitmap: Bitmap): String? {
    return try {
        // Crear directorio "photos" dentro del almacenamiento interno
        val directory = File(context.filesDir, "photos")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        // Crear archivo con nombre único
        val imageFile = File(directory, "photo_${System.currentTimeMillis()}.jpg")

        // Guardar bitmap como JPEG
        FileOutputStream(imageFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        }

        println("Image saved internally at: ${imageFile.absolutePath}")
        imageFile.absolutePath

    } catch (e: Exception) {
        println("Error saving image: ${e.message}")
        e.printStackTrace()
        null
    }
}
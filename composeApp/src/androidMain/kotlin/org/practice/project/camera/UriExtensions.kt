package org.practice.project.camera

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.InputStream


fun Uri.toBitMap(contentResolver: ContentResolver): Bitmap? {
    return runCatching {
        val inputStreamForBitMap = contentResolver.openInputStream(this)
        val bitmap = inputStreamForBitMap?.use {
            BitmapFactory.decodeStream(it)
        }

        val inputStreamForExif = contentResolver.openInputStream(this)
        val rotatedBitMap = bitmap?.rotatIfIsRequired(inputStreamForExif)

        rotatedBitMap
    }.getOrElse {
        null
    }
}


fun Bitmap.rotatIfIsRequired(inputStream: InputStream?): Bitmap {
    if(inputStream == null) return this

    return runCatching {
        val exif = ExifInterface(inputStream)

        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        val matrix = Matrix()
        when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }
        Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }.getOrElse { this }
}
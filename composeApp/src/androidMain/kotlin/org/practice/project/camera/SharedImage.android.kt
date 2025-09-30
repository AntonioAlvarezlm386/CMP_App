package org.practice.project.camera

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class SharedImage(
    private val bitmap: Bitmap?
){
    actual fun toImageBitMap(): ImageBitmap? {
        return bitmap?.asImageBitmap()
    }

    actual fun toByteArray(): ByteArray? {
        return bitmap?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.toByteArray()
        }
    }
}
package org.practice.project.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import org.practice.project.R
import java.io.File
import kotlin.time.Clock

class AndroidFileProvider: FileProvider(
    R.xml.path_provider
) {
    companion object {
        fun getImageUri(context: Context): Uri? = runCatching {
            val tempFile = File.createTempFile(
                "picture_${System.currentTimeMillis()}",
                ".png",
                context.cacheDir
            ).apply {
                createNewFile()
            }

            val authority = "${context.applicationContext.packageName}.provider"

            getUriForFile(
                context,
                authority,
                tempFile
            )
        }.getOrNull()

    }
}
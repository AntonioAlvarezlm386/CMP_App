package org.practice.project.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import org.practice.project.R
import java.io.File

class AndroidFileProvider: FileProvider(

) {
    companion object {
        fun createTempFileUri(
            context: Context,
            prefix: String,
            extensions: String
        ): Uri? = runCatching {
            val tempFile = File.createTempFile(
                "${prefix}_${System.currentTimeMillis()}",
                ".${extensions}}",
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
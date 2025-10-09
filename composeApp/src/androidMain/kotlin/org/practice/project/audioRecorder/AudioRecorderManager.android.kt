package org.practice.project.audioRecorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.File


actual class SharedAudio(
    private val filePath: String,
    private val fileName: String
) {
    actual  val path: String get() = filePath
    actual  val name: String get() = fileName

    private val file: java.io.File get() = java.io.File(filePath)

    actual fun toByteArray(): ByteArray? {
        return file.takeIf { it.exists() }?.readBytes()
    }
}


actual class AudioRecorder actual constructor(private val onLaunch: () -> Unit) {
    actual fun start() {
        onLaunch()
    }

    actual fun stop() {
        //logica esta en rememmber audio contriller
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
actual fun rememberAudioRecorder(onResult: (SharedAudio?) -> Unit): AudioRecorder {
    val context = LocalContext.current
    var audioRecorder: MediaRecorder? by remember { mutableStateOf(null) }
    var currentAudioFile: SharedAudio? by remember { mutableStateOf(null) }
    var isRecording by remember { mutableStateOf(false) }


    return remember {
        AudioRecorder(
            onLaunch = {
                if (!isRecording) {
                    // INICIAR GRABACIÓN
                    try {
                        val audioFile = createTempAudioFile()
                        currentAudioFile = audioFile

                        MediaRecorder(context).apply {
                            setAudioSource(MediaRecorder.AudioSource.MIC)
                            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                            setAudioSamplingRate(44100)
                            setAudioEncodingBitRate(128000)
                            setOutputFile(audioFile.path)

                            prepare()
                            start()

                            audioRecorder = this
                            isRecording = true
                        }
                    } catch (e: Exception) {
                        onResult(null)
                    }
                } else {
                    // DETENER GRABACIÓN
                    try {
                        audioRecorder?.apply {
                            stop()
                            release()
                        }
                        audioRecorder = null
                        isRecording = false

                        // Verificar que el archivo se creó correctamente
                        currentAudioFile?.let { audioFile ->
                            if (File(audioFile.path).exists()) {
                                onResult(audioFile)
                            } else {
                                onResult(null)
                            }
                        } ?: onResult(null)

                    } catch (e: Exception) {
                        onResult(null)
                    }
                }
            }
        )
    }
}

actual fun createTempAudioFile(): SharedAudio {
//    val timeStamp = java.text.SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault())
//        .format(java.util.Date())
//    val fileName = "AUDIO_${timeStamp}.m4a"
//    val cacheDir = AndroidContextHandler.context.cacheDir
//    val filePath = java.io.File(cacheDir, fileName).absolutePath
//
//    return SharedAudio(filePath, fileName)
    TODO()
}
package org.practice.project.audioRecorder

import androidx.compose.runtime.Composable

expect class SharedAudio {
    val path: String
    val name: String
    fun toByteArray(): ByteArray?
}


expect fun createTempAudioFile(): SharedAudio


@Composable
expect fun rememberAudioRecorder(onResult: (SharedAudio?) -> Unit): AudioRecorder


expect class AudioRecorder(onLaunch: () -> Unit){
    fun start()
    fun stop()
}
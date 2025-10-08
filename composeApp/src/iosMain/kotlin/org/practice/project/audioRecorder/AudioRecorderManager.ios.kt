package org.practice.project.audioRecorder

import androidx.compose.runtime.Composable

actual class SharedAudio {
    actual val path: String
        get() = TODO("Not yet implemented")
    actual val name: String
        get() = TODO("Not yet implemented")

    actual fun toByteArray(): ByteArray? {
        TODO("Not yet implemented")
    }
}

actual fun createTempAudioFile(): SharedAudio {
    TODO("Not yet implemented")
}

actual class AudioRecorder actual constructor(onLaunch: () -> Unit) {
    actual fun start() {
    }

    actual fun stop() {
    }
}

@Composable
actual fun rememberAudioRecorder(onResult: (SharedAudio?) -> Unit): AudioRecorder {
    TODO("Not yet implemented")
}
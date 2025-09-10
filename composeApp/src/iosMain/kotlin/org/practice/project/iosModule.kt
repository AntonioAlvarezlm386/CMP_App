package org.practice.project

import org.koin.dsl.module
import org.practice.project.navigation.main.camera.data.platform.CameraManager
import org.practice.project.navigation.main.camera.data.platform.FileManager

val iosModule = module {
    single { CameraManager() }
    single { FileManager() }
}
package org.practice.project
// androidMain/kotlin/di/AndroidModule.kt
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.practice.project.navigation.main.camera.data.platform.CameraManager
import org.practice.project.navigation.main.camera.data.platform.FileManager

val androidModule = module {
    single { CameraManager(androidContext()) }
    single { FileManager(androidContext()) }
}
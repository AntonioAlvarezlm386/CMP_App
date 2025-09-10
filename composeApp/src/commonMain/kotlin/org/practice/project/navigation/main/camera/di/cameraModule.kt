package org.practice.project.navigation.main.camera.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.practice.project.navigation.main.camera.data.repository.CameraRepositoryImpl
import org.practice.project.navigation.main.camera.data.repository.FileRepositoryImpl
import org.practice.project.navigation.main.camera.domain.repository.CameraRepository
import org.practice.project.navigation.main.camera.domain.repository.FileRepository
import org.practice.project.navigation.main.camera.domain.useCases.DeletePhotoUseCase
import org.practice.project.navigation.main.camera.domain.useCases.GetPhotosUseCase
import org.practice.project.navigation.main.camera.domain.useCases.TakePhotoUseCase
import org.practice.project.navigation.main.camera.ui.CameraViewModel


val cameraModule: Module = module {
    single { TakePhotoUseCase(get()) }
    single { GetPhotosUseCase(get()) }
    single { DeletePhotoUseCase(get()) }

    // Repositories
    single<CameraRepository> { CameraRepositoryImpl(get(), get()) }
    single<FileRepository> { FileRepositoryImpl(get()) }

    // ViewModel
    viewModel { CameraViewModel(get(), get(), get()) }
}
package org.practice.project.navigation.main.camera.domain.useCases

import org.practice.project.navigation.main.camera.domain.model.PhotoData
import org.practice.project.navigation.main.camera.domain.repository.CameraRepository

class TakePhotoUseCase(
    private val cameraRepository: CameraRepository
) {
    suspend operator fun invoke(): Result<PhotoData> {
        return cameraRepository.takePhoto()
    }
}
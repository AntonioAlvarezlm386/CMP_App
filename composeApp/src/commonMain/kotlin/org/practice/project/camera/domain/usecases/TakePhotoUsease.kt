package org.practice.project.camera.domain.usecases

import org.practice.project.camera.domain.model.PhotoData
import org.practice.project.camera.domain.repository.CameraRepository


class TakePhotoUseCase(
    private val cameraRepository: CameraRepository
) {
    suspend operator fun invoke(): Result<PhotoData> {
        return cameraRepository.takePhoto()
    }
}
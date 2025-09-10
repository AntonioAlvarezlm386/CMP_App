package org.practice.project.navigation.main.camera.domain.useCases

import org.practice.project.navigation.main.camera.domain.repository.CameraRepository

class DeletePhotoUseCase(
    private val cameraRepository: CameraRepository
) {
    suspend operator fun invoke(photoId: String): Result<Unit> {
        return cameraRepository.deletePhoto(photoId)
    }
}
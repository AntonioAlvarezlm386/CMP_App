package org.practice.project.camera.domain.usecases

import org.practice.project.camera.domain.repository.CameraRepository


class DeletePhotoUseCase(
    private val cameraRepository: CameraRepository
) {
    suspend operator fun invoke(photoId: String): Result<Unit> {
        return cameraRepository.deletePhoto(photoId)
    }
}
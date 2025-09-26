package org.practice.project.camera.domain.usecases

import org.practice.project.camera.domain.model.PhotoData
import org.practice.project.camera.domain.repository.CameraRepository


class GetPhotosUseCase(
    private val cameraRepository: CameraRepository
) {
    suspend operator fun invoke(): Result<List<PhotoData>> {
        return cameraRepository.getAllPhotos()
    }
}
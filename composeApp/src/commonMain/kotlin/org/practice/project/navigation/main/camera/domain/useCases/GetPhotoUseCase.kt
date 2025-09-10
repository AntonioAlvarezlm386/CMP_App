package org.practice.project.navigation.main.camera.domain.useCases

import org.practice.project.navigation.main.camera.domain.model.PhotoData
import org.practice.project.navigation.main.camera.domain.repository.CameraRepository

class GetPhotosUseCase(
    private val cameraRepository: CameraRepository
) {
    suspend operator fun invoke(): Result<List<PhotoData>> {
        return cameraRepository.getAllPhotos()
    }
}

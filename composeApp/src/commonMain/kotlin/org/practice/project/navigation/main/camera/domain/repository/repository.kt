package org.practice.project.navigation.main.camera.domain.repository

import org.practice.project.navigation.main.camera.domain.model.PhotoData

interface CameraRepository {
    suspend fun takePhoto(): Result<PhotoData>
    suspend fun getAllPhotos(): Result<List<PhotoData>>
    suspend fun deletePhoto(photoId: String): Result<Unit>
    suspend fun getPhotoBytes(photoData: PhotoData): Result<ByteArray>
}
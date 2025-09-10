package org.practice.project.navigation.main.camera.data.repository

import org.practice.project.navigation.main.camera.data.platform.FileManager
import org.practice.project.navigation.main.camera.domain.repository.FileRepository

class FileRepositoryImpl(
    private val fileManager: FileManager
) : FileRepository {

    override suspend fun savePhoto(bytes: ByteArray, fileName: String): Result<String> {
        return fileManager.savePhotoToGallery(bytes, fileName)
    }

    override suspend fun getPhoto(filePath: String): Result<ByteArray> {
        return fileManager.getPhotoFromPath(filePath)
    }

    override suspend fun deletePhoto(filePath: String): Result<Unit> {
        return fileManager.deletePhotoFromPath(filePath)
    }

    override suspend fun getAllPhotoPaths(): Result<List<String>> {
        return fileManager.getAllPhotoFiles()
    }
}
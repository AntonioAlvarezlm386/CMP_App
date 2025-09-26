package org.practice.project.camera.domain.repository






interface FileRepository {
    suspend fun savePhoto(bytes: ByteArray, fileName: String): Result<String>
    suspend fun getPhoto(filePath: String): Result<ByteArray>
    suspend fun deletePhoto(filePath: String): Result<Unit>
    suspend fun getAllPhotoPaths(): Result<List<String>>
}
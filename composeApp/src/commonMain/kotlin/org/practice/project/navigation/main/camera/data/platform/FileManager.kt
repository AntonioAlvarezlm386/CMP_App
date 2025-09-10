package org.practice.project.navigation.main.camera.data.platform

expect class FileManager(context: Any? = null) {
    suspend fun savePhotoToGallery(bytes: ByteArray, fileName: String): Result<String>
    suspend fun getPhotoFromPath(path: String): Result<ByteArray>
    suspend fun deletePhotoFromPath(path: String): Result<Unit>
    suspend fun getAllPhotoFiles(): Result<List<String>>
    fun getPhotosDirectory(): String
}
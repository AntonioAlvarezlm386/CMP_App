package org.practice.project.InternalStoragePractice.domain

class  SaveImageOnfileStorageUseCAse(
    private val interNalStorageProvider: InterNalStorageProvider
){
    operator suspend fun invoke(bytes: ByteArray, name: String): Result<String?>{
        return runCatching {
            interNalStorageProvider.saveImage(bytes, name)
        }
    }
}
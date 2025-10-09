package org.practice.project.InternalStoragePractice.domain

interface InterNalStorageProvider {
    suspend fun saveImage(bytes: ByteArray, name: String): String?
}
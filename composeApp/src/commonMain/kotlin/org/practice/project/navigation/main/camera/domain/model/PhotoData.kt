package org.practice.project.navigation.main.camera.domain.model

data class PhotoData(
    val id: String,
    val fileName: String,
    val filePath: String,
    val timestamp: Long,
    val size: Long
)
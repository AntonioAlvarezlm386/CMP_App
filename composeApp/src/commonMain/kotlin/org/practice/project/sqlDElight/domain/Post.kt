package org.practice.project.sqlDElight.domain

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int,
    /*@Transient*/
    val thumbnail: String = "thumbnail.png",
    val id: Int,
    val title: String,
    val body: String
)
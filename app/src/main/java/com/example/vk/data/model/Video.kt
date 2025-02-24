package com.example.vk.data.model

data class Video(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: Double,
    val videoUrl: String,
    val imageData: ByteArray? = null
)
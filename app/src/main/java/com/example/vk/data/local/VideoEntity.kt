package com.example.vk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vk.data.model.Video


@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: Double,
    val videoUrl: String
)

fun VideoEntity.toDomain() = Video(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    duration = duration,
    videoUrl = videoUrl
)

fun Video.toEntity() = VideoEntity(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    duration = duration,
    videoUrl = videoUrl
)
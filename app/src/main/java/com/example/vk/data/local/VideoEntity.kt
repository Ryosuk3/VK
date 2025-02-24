package com.example.vk.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vk.data.model.Video


@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnailUrl: String,  // Ссылка на превью
    val duration: Double,
    val videoUrl: String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) // Хранение изображения в виде массива байтов
    val imageData: ByteArray? = null
)

// Конвертеры для преобразования данных при сохранении в Room
fun VideoEntity.toDomain() = Video(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    duration = duration,
    videoUrl = videoUrl,
    imageData = imageData
)

fun Video.toEntity() = VideoEntity(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    duration = duration,
    videoUrl = videoUrl,
    imageData = imageData
)
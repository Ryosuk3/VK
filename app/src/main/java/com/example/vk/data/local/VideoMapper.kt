package com.example.vk.data.local

import com.example.vk.data.model.Video

fun VideoEntity.toDomain(): Video{
    return Video(
        id = this.id,
        title = this.title,
        thumbnailUrl = this.thumbnailUrl,
        duration = this.duration
    )
}

fun Video.toEntity(): VideoEntity{
    return VideoEntity(
        id = this.id,
        title = this.title,
        thumbnailUrl = this.thumbnailUrl,
        duration = this.duration
    )
}
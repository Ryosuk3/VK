package com.example.vk.data.model

import com.google.gson.annotations.SerializedName

data class ApiVideoResponse(
    @SerializedName("data") val data: List<VideoItem>
)

data class VideoItem(
    @SerializedName("videoId") val videoId: String,
    @SerializedName("title") val title: String,
    @SerializedName("assets") val assets: VideoAssets,
    @SerializedName("duration") val duration: Double? // Может быть null
)

data class VideoAssets(
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("mp4") val mp4: String,
    @SerializedName("hls") val hls: String
)

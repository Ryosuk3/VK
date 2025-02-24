package com.example.vk.data.model

import com.google.gson.annotations.SerializedName

data class VideoStatusResponse(
    @SerializedName("encoding") val encoding: EncodingInfo
)

data class EncodingInfo(
    @SerializedName("metadata") val metadata: VideoMetadata?
)

data class VideoMetadata(
    @SerializedName("duration") val duration: Double?
)

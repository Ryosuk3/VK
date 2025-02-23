package com.example.vk.data.model

data class VideoResponse(
    val items: List<VideoItem>
)

data class VideoItem(
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails
)

data class Snippet(
    val title: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(
    val maxres: Thumbnail?,
    val high: Thumbnail
)

data class Thumbnail(
    val url: String,
    val width: Int?,
    val height: Int?
)

data class ContentDetails(
    val duration: String
)
package com.example.vk.domain.repository

import com.example.vk.data.model.Video
import com.example.vk.data.network.YoutubeApiService

class VideoRepository(private val apiService: YoutubeApiService) {
    suspend fun getVideos(apiKey: String): List<Video> {
        return try {
            val response = apiService.getPopularVideos(apiKey = apiKey)
            response.items.mapNotNull { item ->
                val duration = parseDuration(item.contentDetails.duration)

                if (duration>180) {
                    Video(
                        id = item.id,
                        title = item.snippet.title,
                        thumbnailUrl = item.snippet.thumbnails.maxres?.url ?: item.snippet.thumbnails.high.url,
                        duration = item.contentDetails.duration
                    )
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }



    private fun parseDuration(duration: String): Int {
        val regex = Regex("PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?")
        val matchResult = regex.matchEntire(duration)
        val hours = matchResult?.groups?.get(1)?.value?.toIntOrNull() ?: 0
        val minutes = matchResult?.groups?.get(2)?.value?.toIntOrNull() ?: 0
        val seconds = matchResult?.groups?.get(3)?.value?.toIntOrNull() ?: 0
        return hours * 3600 + minutes * 60 + seconds
    }
}

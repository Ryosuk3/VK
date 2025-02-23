package com.example.vk.domain.repository

import com.example.vk.data.local.VideoDao
import com.example.vk.data.local.VideoEntity
import com.example.vk.data.local.toDomain
import com.example.vk.data.model.Video
import com.example.vk.data.network.YoutubeApiService

class VideoRepository(private val apiService: YoutubeApiService, private val videoDao: VideoDao) {
    suspend fun getVideos(apiKey: String, isNetworkAvailable: Boolean): List<Video> {
        val cachedVideos = videoDao.getAllVideos().map { it.toDomain() }

        if (!isNetworkAvailable){
            return cachedVideos
        }

        return try {
            val response = apiService.getPopularVideos(apiKey = apiKey)
            val videos = response.items.mapNotNull { item ->
                val duration = parseDuration(item.contentDetails.duration)

                if (duration > 180) {
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

            if (videos.isNotEmpty()) {
                videoDao.clearVideos()
                videoDao.insertVideos(videos.map { it.toEntity() })
            }

            videos



        } catch (e: Exception) {
            cachedVideos
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

fun Video.toEntity(): VideoEntity {
    return VideoEntity(
        id = this.id,
        title = this.title,
        thumbnailUrl = this.thumbnailUrl,
        duration = this.duration
    )
}

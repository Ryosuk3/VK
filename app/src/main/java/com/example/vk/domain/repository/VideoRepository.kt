package com.example.vk.domain.repository

import com.example.vk.data.local.VideoDao
import com.example.vk.data.local.toDomain
import com.example.vk.data.local.toEntity
import com.example.vk.data.model.Video
import com.example.vk.data.network.ApiVideoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository(private val apiService: ApiVideoService, private val videoDao: VideoDao) {

    suspend fun getVideos(apiKey: String, isNetworkAvailable: Boolean): List<Video> {
        val cachedVideos = videoDao.getAllVideos().map { it.toDomain() }

        if (!isNetworkAvailable) {
            return cachedVideos
        }

        return try {
            val response = apiService.getVideos("Bearer $apiKey")
            val videos = response.data.map { item ->
                var duration = item.duration

                if (duration == null || duration == 0.0) {
                    duration = fetchVideoDuration(apiKey, item.videoId)
                }

                val videoUrl = item.assets.hls ?: item.assets.mp4

                Video(
                    id = item.videoId,
                    title = item.title,
                    thumbnailUrl = item.assets.thumbnail,
                    duration = duration ?: 0.0,
                    videoUrl = videoUrl
                )
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

    private suspend fun fetchVideoDuration(apiKey: String, videoId: String): Double? {
        return withContext(Dispatchers.IO) {
            try {
                val statusResponse = apiService.getVideoStatus("Bearer $apiKey", videoId)
                statusResponse.encoding.metadata?.duration
            } catch (e: Exception) {
                null
            }
        }
    }
}




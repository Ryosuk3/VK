package com.example.vk.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.example.vk.data.local.VideoDao
import com.example.vk.data.local.toDomain
import com.example.vk.data.local.toEntity
import com.example.vk.data.model.Video
import com.example.vk.data.network.ApiVideoService
import kotlinx.coroutines.Dispatchers
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


class VideoRepository(private val apiService: ApiVideoService, private val videoDao: VideoDao, private val context: Context) {

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

                val imageData = fetchThumbnailImage(item.assets.thumbnail)

                Video(
                    id = item.videoId,
                    title = item.title,
                    thumbnailUrl = item.assets.thumbnail,
                    duration = duration ?: 0.0,
                    videoUrl = videoUrl,
                    imageData = imageData
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

    private suspend fun fetchThumbnailImage(imageUrl: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get()

                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.toByteArray()
            } catch (e: Exception) {
                null
            }
        }
    }
}




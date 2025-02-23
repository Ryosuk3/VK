package com.example.vk.domain.repository

import com.example.vk.data.model.Video
import com.example.vk.data.network.YoutubeApiService

class VideoRepository(private val apiService: YoutubeApiService){
    suspend fun getVideos(apiKey: String): List<Video>{
        return try{
            val response = apiService.getPopularVideos(apiKey = apiKey)
            response.items.map {
                Video(
                    id = it.id,
                    title = it.snippet.title,
                    thumbnailUrl = it.snippet.thumbnails.high.url,
                    duration = it.contentDetails.duration
                )
            }
        } catch (e: Exception){
            emptyList()
        }
    }
}
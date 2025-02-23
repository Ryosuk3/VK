package com.example.vk.data.network

import com.example.vk.data.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService{
    @GET("videos")
    suspend fun getPopularVideos(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResults") maxResults: Int = 10,
        @Query("regionCode") region: String = "RU",
        @Query("key") apiKey: String
    ): VideoResponse
}
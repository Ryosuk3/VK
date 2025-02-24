package com.example.vk.data.network

import com.example.vk.data.model.ApiVideoResponse
import com.example.vk.data.model.VideoStatusResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiVideoService {

    @GET("videos")
    suspend fun getVideos(
        @Header("Authorization") apiKey: String,
        @Query("currentPage") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): ApiVideoResponse

    @GET("videos/{videoId}/status")
    suspend fun getVideoStatus(
        @Header("Authorization") apiKey: String,
        @Path("videoId") videoId: String
    ): VideoStatusResponse
}

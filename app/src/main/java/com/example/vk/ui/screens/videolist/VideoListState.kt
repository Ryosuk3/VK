package com.example.vk.ui.screens.videolist

import com.example.vk.data.model.Video

sealed class  VideoListState{
    object Loading: VideoListState()
    data class Success(val videos: List<Video>): VideoListState()
    data class Error(val message: String): VideoListState()
}
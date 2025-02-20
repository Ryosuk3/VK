package com.example.vk.ui.screens.videolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk.data.model.Video
import com.example.vk.domain.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoListViewModel(private val repository: VideoRepository): ViewModel(){
    private val _videos=MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> get() = _videos

    fun fetchVideos(apiKey: String){
        viewModelScope.launch {
            _videos.value=repository.getVideos(apiKey)
        }
    }
}
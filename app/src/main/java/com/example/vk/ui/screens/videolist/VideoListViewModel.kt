package com.example.vk.ui.screens.videolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk.data.model.Video
import com.example.vk.domain.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoListViewModel(private val repository: VideoRepository): ViewModel(){
//    private val _videos=MutableStateFlow<List<Video>>(emptyList())
//    val videos: StateFlow<List<Video>> get() = _videos
    private val _state = MutableStateFlow<VideoListState>(VideoListState.Loading)
    val state: StateFlow<VideoListState> get() = _state

    fun fetchVideos(apiKey: String, onRefreshComplete: (() -> Unit)? = null){
        viewModelScope.launch {
            //_videos.value=repository.getVideos(apiKey)
            try{
                val videos = repository.getVideos(apiKey)
                if (videos.isNotEmpty()){
                    _state.value=VideoListState.Success(videos)
                }
                else{
                    _state.value=VideoListState.Error("Нет видео для отображения")
                }
            } catch (e: Exception){
                _state.value=VideoListState.Error("Ошибка загрузки данных: ${e}")
            } finally {
                onRefreshComplete?.invoke()
            }
        }
    }
}
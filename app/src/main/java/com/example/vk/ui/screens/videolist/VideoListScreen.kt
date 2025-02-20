package com.example.vk.ui.screens.videolist

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoListScreen(
    apiKey: String,
    onVideoClick: (String) -> Unit,
    viewModel: VideoListViewModel = koinViewModel()
){

}
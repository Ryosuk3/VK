package com.example.vk.ui.screens.videolist

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.vk.data.model.Video
import com.example.vk.utils.NetworkUtils
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.koinViewModel


@Composable
fun VideoListScreen(
    apiKey: String,
    onVideoClick: (String) -> Unit,
    viewModel: VideoListViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    context: Context
) {
    val state by viewModel.state.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    fun refreshVideos() {
        isRefreshing = true
        viewModel.fetchVideos(apiKey, isNetworkAvailable = NetworkUtils.isNetworkAvailable(context)) {
            isRefreshing = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchVideos(apiKey, isNetworkAvailable = NetworkUtils.isNetworkAvailable(context))
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { refreshVideos() },
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is VideoListState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                is VideoListState.Success -> {
                    val videos = (state as VideoListState.Success).videos
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(videos) { video ->
                            VideoItem(video = video) {
                                val encodedUrl = Uri.encode(video.videoUrl)
                                onVideoClick(encodedUrl)
                            }
                        }
                    }
                }
                is VideoListState.Error -> Text(
                    text = (state as VideoListState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
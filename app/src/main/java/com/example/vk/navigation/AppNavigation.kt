package com.example.vk.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.*
import com.example.vk.ui.screens.videolist.VideoListScreen
import com.example.vk.ui.screens.videoplayer.VideoPlayerScreen


@Composable
fun AppNavigation(apiKey: String, innerPadding: PaddingValues) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "video_list") {
        composable("video_list") {
            VideoListScreen(
                apiKey = apiKey,
                onVideoClick = { videoId ->
                    navController.navigate("video_player/$videoId")
                },
                innerPadding = innerPadding
            )
        }
        composable("video_player/{videoId}") { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            VideoPlayerScreen(videoId)
        }
    }
}
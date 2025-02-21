package com.example.vk.ui.screens.videoplayer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun VideoPlayerScreen(
    videoId: String
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            "VideoPlayer",
            fontSize = 24.sp,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,

            )
        Text(text = "Видео ID: $videoId")
    }
}
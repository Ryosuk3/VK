package com.example.vk.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun ControlButtons(player: ExoPlayer, onToggleFullScreen: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { player.seekTo(player.currentPosition - 10000) }) {
            Text("⏪ -10s")
        }
        Button(onClick = {
            if (player.isPlaying) player.pause() else player.play()
        }) {
            Text(if (player.isPlaying) "⏸ Pause" else "▶ Play")
        }
        Button(onClick = { player.seekTo(player.currentPosition + 10000) }) {
            Text("⏩ +10s")
        }
        Button(onClick = onToggleFullScreen) {
            Text("⛶ Fullscreen")
        }
    }
}
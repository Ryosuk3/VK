package com.example.vk.utils

import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


fun formatDuration(durationSeconds: Double): String{
    if (durationSeconds == null || durationSeconds <= 0.0) {
        return "--:--"
    }

    val hours = TimeUnit.SECONDS.toHours(durationSeconds.toLong())
    val minutes = TimeUnit.SECONDS.toMinutes(durationSeconds.toLong()) % 60
    val seconds = durationSeconds.toLong() % 60

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds) // Формат HH:MM:SS
    } else {
        String.format("%02d:%02d", minutes, seconds) // Формат MM:SS
    }
}
package com.example.vk.utils

import java.util.regex.Pattern


fun formatDuration(duration: String): String{
    /*
    на входе сначала PT, потом может быть а может не быть от 1 и до 2 цифр и буква H,
    затем от 0 до 2 цифр и буква M,
    затем от 1 до 2 цифр и буква S
    */
    val pattern = Pattern.compile("PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?")
    val matcher = pattern.matcher(duration)

    if (!matcher.matches()) return "00:00"

    val hours = matcher.group(1)?.toIntOrNull()?: 0
    val minutes = matcher.group(2)?.toIntOrNull()?: 0
    val seconds = matcher.group(3)?.toIntOrNull()?: 0

    if (hours>0){
        return String.format("%d:%02d:%02d", hours, minutes, seconds)
    }
    else{
        return String.format("%d:%02d", minutes, seconds)
    }
}
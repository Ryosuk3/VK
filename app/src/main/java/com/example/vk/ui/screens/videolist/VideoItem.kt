package com.example.vk.ui.screens.videolist

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vk.data.model.Video
import com.example.vk.utils.formatDuration
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun VideoItem(video: Video, onClick: (Video) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(video) }
    ) {
        Column {
            Box {
                if (video.imageData != null) {
                    val bitmap =
                        BitmapFactory.decodeByteArray(video.imageData, 0, video.imageData.size)
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Thumbnail",
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    AsyncImage(
                        model = video.thumbnailUrl,
                        contentDescription = "Thumbnail",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                    )
                }

                Text(
                    text = formatDuration(video.duration),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = video.title,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

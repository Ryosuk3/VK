package com.example.vk.ui.screens.videolist

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vk.data.model.Video
import com.example.vk.ui.theme.LightWhite


@Composable
fun VideoItem(video: Video, onClick: (Video) -> Unit){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(video) }
    ){
        Row(modifier=Modifier.padding(8.dp)){
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = "Thumbnail",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier=Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ){
                Text(text=video.title, color = Color.White)
                Text(text = "Длительность: ${video.duration[2]}:${video.duration[]}", color = LightWhite)
            }
        }
    }
}
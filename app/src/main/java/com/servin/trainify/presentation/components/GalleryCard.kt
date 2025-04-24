package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.servin.trainify.R
import com.servin.trainify.exercises.data.model.Exercise

@Composable

fun GalleryCard(url: String?) {

    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .widthIn(max = 300.dp)
            .aspectRatio(16/13f)
            .fillMaxWidth(0.9f),
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        error = painterResource(id = R.drawable.ic_launcher_background),
        contentScale = ContentScale.Crop
    )

}


@Preview
@Composable
fun GalleryCardPreview() {
    GalleryCard("")
}
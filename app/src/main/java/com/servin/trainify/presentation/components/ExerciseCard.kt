package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.servin.trainify.R
import com.servin.trainify.exercises.data.model.Exercise

@Composable
fun ExerciseCard(exercise: Exercise) {
    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .width(150.dp)
    ) {
        AsyncImage(
            model = exercise.mediaUrls.firstOrNull(),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
        )

        Text(
            text = exercise.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}
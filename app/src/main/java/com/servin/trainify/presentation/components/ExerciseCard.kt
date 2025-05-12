package com.servin.trainify.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.servin.trainify.R
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.navigation.AppDestination

@Composable
fun ExerciseCard(exercise: Exercise, navigate: (String) -> Unit, isSelected: Boolean? = false) {
    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .widthIn(max = 200.dp)
            .fillMaxWidth(0.8f)
    ) {
        AsyncImage(
            model = exercise.mediaUrls.firstOrNull(),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20.dp))
                .then(if (isSelected == true) Modifier.border(3.dp, Color.White, RoundedCornerShape(20.dp)) else Modifier)
                .clickable { navigate(exercise.id) },
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop
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

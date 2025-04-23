package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.servin.trainify.R

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.servin.trainify.exercises.data.model.Exercise

@Composable
fun ExerciseGalleryGrouped(exercises: List<Exercise>) {
    val grouped = exercises.groupBy { it.objective }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        grouped.forEach { (objective, exercisesInGroup) ->
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = objective,
                        fontSize = 22.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyRow {
                        items(exercisesInGroup) { exercise ->
                            ExerciseCard(exercise)
                        }
                    }
                }
            }
        }
    }
}
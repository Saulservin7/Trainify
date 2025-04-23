package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.servin.trainify.exercises.data.model.Exercise

@Composable
fun GalleryMedia(exercise: Exercise,size:Int,modifier: Modifier){
    LazyRow(modifier = modifier.fillMaxWidth()) {
        items(size) {index->
            GalleryCard(exercise.mediaUrls[index])
        }
    }

}


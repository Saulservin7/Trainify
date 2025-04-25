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


@Composable
@Preview
fun GalleryMediaPreview() {
    val exercise = Exercise(
        id = "1",
        title = "Ejercicio 1",
        description = "Descripción del ejercicio 1",
        mediaUrls = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
        objective = "Objetivo del ejercicio 1",
         sportContext = "Tipo del ejercicio 1",

    )
    GalleryMedia(exercise,2,Modifier.fillMaxWidth())
}


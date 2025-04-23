package com.servin.trainify.exercises.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.presentation.viewmodel.ExerciseDetailViewModel
import com.servin.trainify.presentation.components.GalleryMedia
import com.servin.trainify.presentation.components.TitleScreen
import com.servin.trainify.exercises.domain.model.Result


@Composable
fun ExerciseDetailScreen(exerciseId: String,viewModel: ExerciseDetailViewModel= hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(exerciseId) {
        viewModel.loadExercise(exerciseId)
    }

    when (state) {
        is Result.Loading -> {
            CircularProgressIndicator()
        }

        is Result.Success -> {
            val exercise = (state as Result.Success<Exercise>).data

            ConstraintLayout(modifier = Modifier.fillMaxSize())
            {
                val (title, gallery, description) = createRefs()

                TitleScreen(exercise.title, modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })

                Column(modifier = Modifier.constrainAs(description) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    Text("Descripcion")

                    Text(exercise.title)

                }

                GalleryMedia(exercise,exercise.mediaUrls.size, modifier = Modifier.constrainAs(gallery) {
                    top.linkTo(description.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })




            }
        }

        is Result.Error -> {
            Log.d("Tacos", "Error: ${(state as Result.Error).message}")
        }

        Result.Idle -> {
            Text("Cargando...")
        }
    }


}







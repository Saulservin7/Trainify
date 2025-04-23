package com.servin.trainify.exercises.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.presentation.viewmodel.AllExercisesViewModel
import com.servin.trainify.presentation.components.ExerciseGalleryGrouped
import com.servin.trainify.presentation.components.TitleScreen

@Composable
fun AllExercisesScreen(viewModel: AllExercisesViewModel = hiltViewModel(),navigate: (String) -> Unit) {
    val result by viewModel.state.collectAsState()

    when (result) {
        is Result.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Result.Success -> {
            val exercises = (result as Result.Success<List<Exercise>>).data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TitleScreen("Ejercicios", modifier = Modifier.padding(16.dp))

                // Aquí puedes mostrar la lista de ejercicios
                ExerciseGalleryGrouped(exercises,navigate)

            }


        }

        is Result.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Error al cargar ejercicios: ${(result as Result.Error).message}",
                    color = Color.Red
                )
            }
        }

        Result.Idle -> {
            // Nada por mostrar aún, puedes dejar vacío o mostrar algo neutral
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cargando ejercicios...")
            }
        }


    }
}


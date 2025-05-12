package com.servin.trainify.routines.presentation.views

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.presentation.viewmodel.AllExercisesViewModel
import com.servin.trainify.presentation.components.ExerciseGalleryGrouped
import com.servin.trainify.presentation.components.TitleScreen

@Composable

fun SelectExercises(viewModel: AllExercisesViewModel= hiltViewModel(),isSelect:Boolean=false,navigate: (String) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {

        val result by viewModel.state.collectAsState()

        when (result) {
            is Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Result.Success -> {
                val exercises = (result as Result.Success<List<Exercise>>).data

                val selectedExerciseIds = remember { mutableStateListOf<String>() }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    TitleScreen("Ejercicios", modifier = Modifier.padding(16.dp))
                    ExerciseGalleryGrouped(
                        exercises = exercises,
                        isSelectable = isSelect,
                        selectedIds = selectedExerciseIds,
                        onExerciseClick = { exercise ->
                            if (selectedExerciseIds.contains(exercise.id)) {
                                selectedExerciseIds.remove(exercise.id)
                            } else {
                                selectedExerciseIds.add(exercise.id)
                            }
                            Log.d("ExerciseGalleryGrouped", "IDs seleccionados: $selectedExerciseIds")
                        },
                        navigate = navigate

                    )

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
}

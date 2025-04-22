package com.servin.trainify.exercises.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.auth.presentation.components.FieldForm
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.presentation.viewmodel.ExerciseViewModel
import com.servin.trainify.presentation.components.DropBox
import com.servin.trainify.presentation.components.EstandardButton
import com.servin.trainify.presentation.components.MediaGalleryPicker
import com.servin.trainify.presentation.components.TitleScreen

@Composable
fun AddExerciseScreen() {

    AddExerciseContent()

}

@Composable
fun AddExerciseContent(viewModel: ExerciseViewModel = hiltViewModel()) {

    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val sportContext by viewModel.sportContext.collectAsState()
    val objective by viewModel.objective.collectAsState()
    val mediaList by viewModel.mediaList.collectAsState()
    val id by viewModel.id.collectAsState()


    val SportContextItems = listOf("Gym", "Fútbol", "Basquetbol")
    val ObjectiveItems = listOf("Brazo", "Pierna", "Gluteo", "Espalda", "Pecho", "Hombro", "Abdomen")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        val (titleText, columnForm) = createRefs()

        TitleScreen("Add Exercise", modifier = Modifier.constrainAs(titleText) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)

        })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(columnForm) {
                    top.linkTo(titleText.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .verticalScroll(rememberScrollState())) {

            FieldForm(
                "Titulo", "Ingresa el Titulo del Ejercicio", title.value, { viewModel.setTitle(it) },
                isError = title.isError,
                errorDescription = "Ingresa un Titulo correcto",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            FieldForm(
                "Descripcion", "Ingresa una Breve Descripcion del ejercicio", description.value, { viewModel.setDescription(it) },
                isError = description.isError,
                errorDescription = "Ingresa una Descripcion correcta",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            MediaGalleryPicker(
                mediaUrls = mediaList,
                onAddMedia = { mediaUri ->
                    viewModel.addMedia(mediaUri)
                }
            )

            Row(modifier = Modifier.fillMaxWidth(),Arrangement.SpaceEvenly) {
                DropBox(
                    modifier = Modifier.padding(10.dp),
                    items = SportContextItems,
                    selectedItem = sportContext.value,
                    title = "Disciplina",
                    onItemSelected = { viewModel.setSportContext(it) }
                )

                DropBox(
                    modifier = Modifier.padding(10.dp),
                    items = ObjectiveItems,
                    selectedItem = objective.value,
                    title = "Grupo Muscular",
                    onItemSelected = { viewModel.setObjective(it) }
                )
            }



            EstandardButton(
                text = "Guardar",
                onClick = {
                    val imageUrl = mediaList.find { it.endsWith(".jpg") || it.endsWith(".png") }
                    val videoUrl = mediaList.find { it.endsWith(".mp4") || it.endsWith(".mov") }

                    val exercise = Exercise(
                        id = "1",
                        title = title.value,
                        description = description.value,
                        imageURL = imageUrl,
                        videoURL = videoUrl,
                        objective = objective.value,
                        sportContext = sportContext.value
                    )

                    viewModel.addExercises(exercise)
                },
                modifier = Modifier.fillMaxWidth(),
            )


        }


    }

}


@Preview
@Composable
fun AddExerciseScreenPreview() {
    AddExerciseScreen()
}

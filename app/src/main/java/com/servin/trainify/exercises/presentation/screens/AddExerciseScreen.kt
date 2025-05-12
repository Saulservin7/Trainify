package com.servin.trainify.exercises.presentation.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.auth.presentation.components.FieldForm
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.presentation.viewmodel.ExerciseViewModel
import com.servin.trainify.presentation.components.DropBox
import com.servin.trainify.presentation.components.EstandardButton
import com.servin.trainify.presentation.components.MediaGalleryPicker
import com.servin.trainify.presentation.components.TitleScreen


@Composable
fun AddExerciseScreen(onNavigate: () -> Unit) {

    AddExerciseContent(
        onNavigate = onNavigate
    )

}

@Composable
fun AddExerciseContent(viewModel: ExerciseViewModel = hiltViewModel(),onNavigate: () -> Unit ) {

    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val sportContext by viewModel.sportContext.collectAsState()
    val objective by viewModel.objective.collectAsState()
    val mediaList by viewModel.mediaList.collectAsState()
    val id by viewModel.id.collectAsState()
    val context = LocalContext.current
    val isPublic by viewModel.isPublicState.collectAsState()


    val SportContextItems = listOf("Gym", "Fútbol", "Basquetbol")
    val ObjectiveItems = listOf("Brazo", "Pierna", "Gluteo", "Espalda", "Pecho", "Hombro", "Abdomen")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        val (titleText, columnForm,buttonSave) = createRefs()

        TitleScreen("Agregar Ejercicio", modifier = Modifier.constrainAs(titleText) {
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
                    bottom.linkTo(buttonSave.top, margin = 10.dp)
                    height = Dimension.fillToConstraints
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

            Text("Imagenes y/o Videos", modifier = Modifier.padding(bottom = 10.dp))

            MediaGalleryPicker(
                mediaUrls = mediaList,
                onAddMedia = { mediaUri ->
                    viewModel.addMedia(mediaUri)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DropBox(
                    modifier = Modifier.weight(1f).padding(end = 10.dp),
                    items = SportContextItems,
                    selectedItem = sportContext.value,
                    title = "Disciplina",
                    onItemSelected = { viewModel.setSportContext(it) }
                )

                DropBox(
                    modifier = Modifier.weight(1f).padding(start = 10.dp),
                    items = ObjectiveItems,
                    selectedItem = objective.value,
                    title = "Grupo Muscular",
                    onItemSelected = { viewModel.setObjective(it) }
                )
            }

            Text("¿Hacer público el ejercicio?", modifier = Modifier.padding(bottom = 10.dp))
            Switch(
                checked = isPublic,
                onCheckedChange = { viewModel.setIsPublic(it) }
            )



        }
        EstandardButton(
            text = "Guardar",
            onClick = {
                // Obtiene la lista de imágenes y videos usando la función del ViewModel
                val (imageUrls, videoUrls) = viewModel.processMedia(context)
                val mediaUris = imageUrls + videoUrls

                Log.d("FilteredMedia", "Images: $imageUrls, Videos: $videoUrls")

                val exercise = Exercise(
                    id = "1",  // El ID final se genera en el repositorio
                    title = title.value,
                    description = description.value,
                    mediaUrls = mediaUris,  // Pasa la lista combinada
                    objective = objective.value,
                    sportContext = sportContext.value,
                    isPublic = isPublic,
                )

                viewModel.addExercises(exercise, mediaUris, "")
                onNavigate()

            },
            modifier = Modifier.constrainAs(buttonSave){
                top.linkTo(columnForm.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, 16.dp)
            }
                .fillMaxWidth()
                .fillMaxHeight(0.06f)
        )


    }

}


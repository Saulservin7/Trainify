package com.servin.trainify.routines.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.auth.presentation.components.FieldForm
import com.servin.trainify.presentation.components.EstandardButton
import com.servin.trainify.presentation.components.ExercisesPreview
import com.servin.trainify.presentation.components.SelectableDaysChipsTwoRows
import com.servin.trainify.presentation.components.TitleScreen
import com.servin.trainify.routines.presentation.viewmodel.RoutinesViewModel

@Composable

fun AddRoutineScreen() {

    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(20.dp)
    ) {
        AddRoutineContent()

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineContent(viewModel: RoutinesViewModel = hiltViewModel()) {
    var showModal by remember { mutableStateOf(false) }

    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val series by viewModel.seriesList.collectAsState()
    val repeticiones by viewModel.repetitions.collectAsState()
    val descanso by viewModel.restTime.collectAsState()
    val isWeight by viewModel.isWeight.collectAsState()
    val weight by viewModel.weight.collectAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (columnForm, buttonSave) = createRefs()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(columnForm) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            TitleScreen("Crear rutina")
            FieldForm(
                title = "Ingresa el titulo de la rutina",
                label = "Nombre de la rutina",
                value = title.value,
                onValueChange = { viewModel.setTitle(it) },
                errorDescription = "Ingresa un titulo valido",
                isError = title.isError,
                modifier = Modifier.fillMaxWidth()
            )

            FieldForm(
                title = "Ingresa una descripcion de la rutina",
                label = "Descripcion",
                value = description.value,
                onValueChange = { viewModel.setDescription(it) },
                errorDescription = "Ingresa una descripcion acorde",
                isError = description.isError,
                modifier = Modifier.fillMaxWidth()

            )
            ExercisesPreview(
                modifier = Modifier.fillMaxWidth(),
                exercisesList = listOf("1", "2", "3", "4", "5"),
                seriesList = series,
                setSeries = { index, value -> viewModel.setSeries(index, value) },
                repeticiones = repeticiones,
                setRepeticiones = { viewModel.setRepetitions(it) },
                descanso = descanso,
                setDescanso = { viewModel.setRestTime(it) },
                isWeight = isWeight,
                setIsWeight = { viewModel.setIsWeight(it) },
                weight = weight,
                setWeight = { viewModel.setWeight(it) },
                openModal = { showModal = true }
            )
            SelectableDaysChipsTwoRows()

            Text("¿Hacer público el ejercicio?", modifier = Modifier.padding(bottom = 10.dp))
            Switch(
                checked = false,
                onCheckedChange = { }
            )

        }

        EstandardButton(
            text = "Guardar Rutina", onClick = {},
            modifier = Modifier.constrainAs(buttonSave) {
                top.linkTo(columnForm.bottom, margin = 20.dp)
                start.linkTo(columnForm.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(columnForm.end)
            },
            enabled = true
        )

    }

    if (showModal) {
        ModalBottomSheet(
            onDismissRequest = { showModal = false },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f), // 75% de alto
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            // Aquí llamas a la pantalla que quieres mostrar
            SelectExercises(
                isSelect = true,
                navigate = { }
            )
        }
    }


}


@Composable
@Preview()
fun AddExerciseScreenPreview() {
    AddRoutineScreen()
}
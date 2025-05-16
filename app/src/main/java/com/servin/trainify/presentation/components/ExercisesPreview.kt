package com.servin.trainify.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.ui.theme.BluePrimary
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesPreview(
    modifier: Modifier,
    exercisesList: List<Exercise> = emptyList(),
    seriesList: List<String>,
    setSeries: (Int, String) -> Unit,
    repeticiones: String,
    setRepeticiones: (String) -> Unit,
    descanso: String,
    setDescanso: (String) -> Unit,
    isWeight: Boolean,
    setIsWeight: (Boolean) -> Unit,
    weight: String,
    setWeight: (String) -> Unit,
    openModal: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "Ejercicios",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 0.dp)
            )

            Text(
                "Añadir Ejercicio(s) ->",
                color = BluePrimary,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable {openModal() }
            )




        }

        val state = rememberCarouselState(itemCount = { exercisesList.size }, initialItem = 0)

        HorizontalMultiBrowseCarousel(
            state = state,
            preferredItemWidth = 300.dp,
            modifier = Modifier.height(750.dp),
            itemSpacing = 10.dp
        ) { page ->
            Log.d("pagina para ver", page.toString())
            val exercise = exercisesList[page]
            val imageUrl = exercise.mediaUrls.firstOrNull()
            Column(modifier = Modifier.fillMaxWidth()) {
                GalleryCard(imageUrl)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Series:",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = seriesList.getOrElse(page) { "" },
                        onValueChange = { setSeries(page, it) },
                        placeholder = { Text("") },
                        modifier = Modifier
                            .width(100.dp)
                    )
                    Text(
                        text = "Repeticiones",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = repeticiones,
                        onValueChange = { setRepeticiones(it) },
                        placeholder = { Text("") },
                        modifier = Modifier
                            .width(100.dp)
                    )

                    Text(
                        text = "Descanso",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = descanso,
                        onValueChange = { setDescanso(it) },
                        placeholder = { Text("") },
                        modifier = Modifier
                            .width(100.dp)
                    )
                    Text("¿Incluye Peso?", modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                    Switch(
                        checked = isWeight,
                        onCheckedChange = { setIsWeight(it) },
                    )
                    Text(
                        text = "Ingresa el peso",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { setWeight(it) },
                        placeholder = { Text("") },
                        modifier = Modifier
                            .width(100.dp)
                    )

                }
            }
        }

    }

}


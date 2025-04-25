package com.servin.trainify.exercises.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.servin.trainify.R
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.presentation.viewmodel.ExerciseDetailViewModel
import com.servin.trainify.presentation.components.GalleryMedia
import com.servin.trainify.presentation.components.TitleScreen
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.presentation.components.Ranking
import com.servin.trainify.presentation.components.StarRating
import com.servin.trainify.presentation.components.StarRatingPreview
import com.servin.trainify.ui.theme.BluePrimary


@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    viewModel: ExerciseDetailViewModel = hiltViewModel(),

) {

    val state by viewModel.state.collectAsState()

    val likeState by viewModel.likeState.collectAsState()

    val rankingState by viewModel.ratingState.collectAsState()

    val scrollState = rememberScrollState()

    LaunchedEffect(exerciseId) {
        viewModel.loadExercise(exerciseId)
    }

    when (state) {
        is Result.Loading -> {
            CircularProgressIndicator()
        }

        is Result.Success -> {
            val exercise = (state as Result.Success<Exercise>).data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp)
            ) {
                ExerciseDetailContent(
                    exercise,
                    likeState,
                    onLikeClicked = { viewModel.setLikeState(!likeState,exerciseId) },
                    viewModel,
                    onRankingClicked = {viewModel.setRatingState(it) },
                    rankingState
                )
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

@Composable
fun ExerciseDetailContent(
    exercise: Exercise,
    likeState: Boolean,
    onLikeClicked: () -> Unit,
    viewModel: ExerciseDetailViewModel,
    onRankingClicked: (Int) -> Unit,
    rankingState: Int
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (title, gallery, description, titleColumn, starRanking, rowHead) = createRefs()



        Row(
            modifier = Modifier
                .constrainAs(rowHead) {
                    top.linkTo(parent.top, margin = 15.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // <-- Esta línea es la clave
        ) {
            TitleScreen(exercise.title, centerVertically = true)

            Ranking(
                modifier = Modifier,
                size = 15.dp
            )

            Icon(
                painter = painterResource(id = if(!likeState) R.drawable.like else R.drawable.like_filled),
                contentDescription = "Favorite",
                tint = BluePrimary,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(32.dp)
                    .clickable
                    {
                        onLikeClicked()
                    }
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(description) {
                    top.linkTo(rowHead.bottom, margin = 20.dp)
                    start.linkTo(title.start)
                }
        ) {
            Text("Descripción", fontSize = 18.sp)
            Text(exercise.description, modifier = Modifier.padding(top = 10.dp))
        }
        Column(modifier = Modifier.constrainAs(gallery) {
            top.linkTo(description.bottom, margin = 40.dp)
            start.linkTo(title.start)
            end.linkTo(parent.end)
        }) {
            Text("Galería", fontSize = 18.sp)
            GalleryMedia(
                exercise,
                exercise.mediaUrls.size,
                modifier = Modifier.padding(top = 15.dp)
            )

        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(titleColumn) {
                    top.linkTo(gallery.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Disciplina", fontSize = 18.sp)
                Text(exercise.sportContext, modifier = Modifier.padding(top = 10.dp))
            }
            Column {
                Text("Grupo Muscular", fontSize = 18.sp)
                Text(exercise.objective, modifier = Modifier.padding(top = 10.dp))
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(starRanking) {
                top.linkTo(titleColumn.bottom, margin = 40.dp)
                start.linkTo(title.start)
            }) {

            Text(
                "Califica Este Ejercicio",
                fontSize = 18.sp,

                )

            StarRating(
                rating = rankingState,
                onRatingChanged = { newRating ->
                    viewModel.setRatingState(newRating)
                },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun PreviewExerciseDetailScreen() {
    val mockExercise = Exercise(
        id = "1",
        title = "Sentadilla",
        description = "Los ejercicios de sentadilla son una excelente manera de trabajar los músculos de las piernas y los glúteos. Puedes hacer sentadillas con peso o sin peso, dependiendo de tu nivel de condición física.",
        mediaUrls = listOf("", ""), // Puedes agregar URLs si deseas
        sportContext = "Fuerza",
        objective = "Piernas"
    )

    ExerciseDetailContent(mockExercise)


}*/
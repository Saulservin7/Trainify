package com.servin.trainify.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.HorizontalAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.servin.trainify.R
import com.servin.trainify.auth.presentation.viewmodel.AuthState
import com.servin.trainify.auth.presentation.viewmodel.AuthViewModel
import com.servin.trainify.data.model.Exercise

import com.servin.trainify.data.model.ExerciseCategoryCard
import com.servin.trainify.data.model.MenuItem
import com.servin.trainify.domain.model.ExerciseCategory
import com.servin.trainify.domain.repository.CategoryRepository
import com.servin.trainify.navbar.components.BottomBar
import com.servin.trainify.presentation.viewmodel.HomeViewModel
import com.servin.trainify.ui.theme.BluePrimary
import com.servin.trainify.ui.theme.gradient

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onLogoutClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),

    ) {

    val authState by viewModel.authstate.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            onLogoutClick()
        }
    }

    HomeContent()


}


@Composable
fun HomeContent(viewModel: HomeViewModel = hiltViewModel()) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        val userName = viewModel.userData.collectAsState()
        val (title, iconTitle, cardProgress, rowButtons, titleExercise, exerciseCards, addExercise) = createRefs()

        Icon(
            painter = painterResource(id = R.drawable.thunder),
            contentDescription = "Thunder",
            modifier = Modifier
                .constrainAs(iconTitle) {
                    top.linkTo(parent.top, margin = 30.dp)
                    start.linkTo(cardProgress.start)

                }
                .size(25.dp), tint = BluePrimary
        )
        Text(
            text = "WELCOME BACK,${userName.value?.name?.uppercase()}",
            modifier = Modifier.constrainAs(title) {
                top.linkTo(iconTitle.top)
                start.linkTo(iconTitle.end)
            }, color = Color.White
        )

        Card(
            modifier = Modifier
                .constrainAs(cardProgress) {
                    top.linkTo(title.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .fillMaxHeight(0.15f),
            colors = CardDefaults.cardColors(contentColor = Color.Transparent)


        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient)

            ) {

                Text(
                    "Progress",
                    modifier = Modifier
                        .padding(top = 24.dp, start = 19.dp),
                    color = Color.White, fontSize = 20.sp
                )

                Text(
                    "Haz Cumplido tu objetivo",
                    modifier = Modifier
                        .padding(top = 63.dp, start = 19.dp),
                    color = Color.White, fontSize = 15.sp
                )


            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(rowButtons) {
                    top.linkTo(cardProgress.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val menuItem: List<MenuItem> = listOf(
                MenuItem(title = "Calendario", icon = R.drawable.calendar),
                MenuItem(title = "Categoria", icon = R.drawable.category),
                MenuItem(title = "Estadisticas", icon = R.drawable.data),
                MenuItem(title = "Más", icon = R.drawable.more)
            )

            menuItem.forEach(
                {
                    MenuButton(title = it.title, icon = it.icon)
                }
            )


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(titleExercise) {
                    top.linkTo(rowButtons.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                }, horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                "Ejercicios",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                "Agregar Ejercicio ->",
                color = BluePrimary,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable { }
            )
        }


        val categories by viewModel.categories.collectAsState()

        LazyRow(
            modifier = Modifier.constrainAs(exerciseCards) {
                top.linkTo(
                    titleExercise.bottom,
                    margin = 20.dp
                )
                start.linkTo(parent.start)
            },
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(categories) { exerciseCategory ->
                ExerciseCard(exerciseCategory)
            }
        }

    }
}


@Composable
fun MenuButton(title: String, icon: Int) {
    Column {
        Card(
            modifier = Modifier
                .size(50.dp) // Tamaño base
                .aspectRatio(1f), // Mantiene relación cuadrada
            shape = RoundedCornerShape(100.dp),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient)
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "Thunder",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(25.dp)
                        .aspectRatio(1f),
                    tint = BluePrimary
                )


            }
        }
        Text(
            text = title,
            color = Color.White,
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(50.dp) // Mismo ancho que el Card
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally)
        )


    }
}


@Composable
fun ExerciseCard(categoryCard: ExerciseCategory) {
    Column {


        Card(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.3f)
                .aspectRatio(0.80f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .background(gradient)
                    // .padding(16.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(categoryCard.image),
                    contentDescription = "Exercise",
                    modifier = Modifier
                        .fillMaxSize()
                )

            }
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = categoryCard.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}


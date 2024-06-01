package com.servin.trainify.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.servin.trainify.R
import com.servin.trainify.navigation.BottomNavigation
import com.servin.trainify.ui.home.model.Exercise
import com.servin.trainify.ui.register.ui.Top
import com.servin.trainify.ui.theme.RedPrimary
import com.servin.trainify.viewmodel.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Home(navController, viewModel)
        }
    }
}

@Composable
fun Home(navController: NavController, viewModel: HomeViewModel) {
    TopBar(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, viewModel: HomeViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Ejercicios", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = RedPrimary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("exercises") },
                containerColor = RedPrimary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = "Agregar", tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        ExercisesList(paddingValues, viewModel)
    }
}

@Composable
fun ExercisesList(it: PaddingValues, viewModel: HomeViewModel) {
    val exercises = viewModel.getAllExercises().collectAsState(initial = emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(it)
    ) {
        items(exercises.value) {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween // Alinea los elementos a los extremos
                    ) {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(
                                start = 10.dp,
                                top = 5.dp
                            )// Alinea el título verticalmente
                        )
                        Row(modifier = Modifier.padding(end = 5.dp)) { // Agrupa los botones
                            EditButton()
                            Spacer(modifier = Modifier.size(5.dp))
                            DeleteButton(it, viewModel)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = it.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                            // Centra la descripción horizontalmente
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun ShowDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar ejercicio") },
        text = { Text("¿Estás seguro de que quieres eliminar este ejercicio?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Sí")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}

@Composable
fun DeleteButton(exercise: Exercise, viewModel: HomeViewModel) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        ShowDialog(
            onConfirm = {
                viewModel.deleteExercise(exercise)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
    IconButton(
        onClick = { showDialog=true},
        modifier = Modifier
            .size(40.dp)
            .background(color = RedPrimary, shape = CircleShape)
            .padding(5.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_delete),
            contentDescription = "Delete",
            tint = Color.White,
            modifier = Modifier.size(25.dp)
        )
    }
}

@Composable
fun EditButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .background(color = RedPrimary, shape = CircleShape)
            .size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_edit),
            contentDescription = "Edit",
            tint = Color.White
        )
    }
}







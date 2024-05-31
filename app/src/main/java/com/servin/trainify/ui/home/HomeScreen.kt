package com.servin.trainify.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.servin.trainify.R
import com.servin.trainify.ui.register.ui.Top
import com.servin.trainify.ui.theme.Red
import com.servin.trainify.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController,viewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Home(navController,viewModel)
    }
}

@Composable
fun Home(navController: NavController,viewModel:HomeViewModel){
    TopBar(navController,viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController,viewModel:HomeViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Ejercicios") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Red)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("exercises") },
                containerColor = Red
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = "Agregar"
                )
            }
        }
    ) { paddingValues ->
        ExercisesList(paddingValues,viewModel)
    }
}

@Composable
fun ExercisesList(it: PaddingValues, viewModel: HomeViewModel) {
    // Recoge el flujo como un estado
    val exercises = viewModel.getAllExercises().collectAsState(initial = emptyList())

    LazyColumn(contentPadding = it) {
        items(exercises.value) {
            Row(modifier = Modifier.padding(5.dp)) {
                // Aquí puedes acceder a las propiedades de 'exercise' para mostrarlas en tu UI
                Text(text = it.description)

                EditButton()
                DeleteButton()
            }
        }
    }
}

@Composable
fun DeleteButton() {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier.size(40.dp) // Tamaño más pequeño del botón
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_delete),
            contentDescription = "Eliminar",
            modifier = Modifier.size(20.dp) // Reducir el tamaño del Icono
        )
    }
}

@Composable
fun EditButton() {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier.size(40.dp) // Tamaño más pequeño del botón
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_edit),
            contentDescription = "Editar",
            modifier = Modifier.size(20.dp) // Reducir el tamaño del Icono
        )
    }
}


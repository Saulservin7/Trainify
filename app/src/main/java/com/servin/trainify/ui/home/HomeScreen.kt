package com.servin.trainify.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Home(navController)
    }
}

@Composable
fun Home(navController: NavController) {
    TopBar(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
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
        ExercisesList(paddingValues)
    }
}

@Composable
fun ExercisesList(it: PaddingValues) {
    LazyColumn(contentPadding = it) {
        items(10) {
            Row(modifier = Modifier.padding(5.dp)) {
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


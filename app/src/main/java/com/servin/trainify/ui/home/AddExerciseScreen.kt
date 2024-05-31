package com.servin.trainify.ui.home

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.servin.trainify.R
import com.servin.trainify.ui.theme.Red
import com.servin.trainify.viewmodel.ExerciseViewModel

@Composable
fun AddExerciseScreen(exerciseViewModel: ExerciseViewModel) {

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(50.dp)) {

        AddExercise(exerciseViewModel)
    }


}

@Composable
fun AddExercise(exerciseViewModel: ExerciseViewModel) {

    val title: String by exerciseViewModel.title.observeAsState("")
    val description: String by exerciseViewModel.description.observeAsState("")
    val image: Bitmap? by exerciseViewModel.image.observeAsState(null)

    Column {
        TitleTextField(title){ exerciseViewModel.onExerciseChanged(it, description, image) }
        DescriptionTextField(description){ exerciseViewModel.onExerciseChanged(title, it, image) }
        ImageCard()
        AddButton(exerciseViewModel)

    }


}

@Composable
fun TitleTextField(title:String, onTitleChanged: (String) -> Unit){
    Text(text = "Título", modifier = Modifier.fillMaxWidth())
    OutlinedTextField(
        value = title,
        onValueChange = {onTitleChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp)
    )


}

@Composable
fun DescriptionTextField(description:String, onDescriptionChanged: (String) -> Unit){
    Text(text = "Descripción", modifier = Modifier.fillMaxWidth())
    OutlinedTextField(
        value = description,
        onValueChange = {onDescriptionChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(){
    Text(text = "Añadir foto o video")
    OutlinedCard(onClick = { /*TODO*/ }, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.3f)
        .padding(top = 10.dp)) {

        Box(contentAlignment = Alignment.Center) {
            Icon(painter = painterResource(id = R.drawable.icon_add_photo), contentDescription = "")
        }


    }
}

@Composable
fun AddButton(viewModel: ExerciseViewModel){
    Row(horizontalArrangement = Arrangement.Center,modifier=Modifier.fillMaxWidth()) {
        Button(onClick = { viewModel.addExercise() }, colors = ButtonDefaults.buttonColors(Color(Red.toArgb())), modifier = Modifier) {

            Text(text = "Añadir Ejercicio")

        }
    }

}




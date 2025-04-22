package com.servin.trainify.profile.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.auth.presentation.components.FieldForm
import com.servin.trainify.presentation.components.DropBox
import com.servin.trainify.presentation.components.EstandardButton
import com.servin.trainify.presentation.components.TitleScreen
import com.servin.trainify.profile.ProfileState
import com.servin.trainify.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onSaveChangesClick: () -> Unit
) {


    val name by viewModel.name.collectAsState()
    val age by viewModel.age.collectAsState()
    val heightUser by viewModel.height.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val gender by viewModel.gender.collectAsState()


    val user = (viewModel.state as? ProfileState.Data)?.user
    if (user != null && name.value.isEmpty() && age.value.isEmpty() && heightUser.value.isEmpty() && weight.value.isEmpty() && gender.value.isEmpty()) {
        viewModel.setName(user.name)
        viewModel.setAge(user.age ?: "")
        viewModel.setHeight(user.height ?: "")
        viewModel.setWeight(user.weight ?: "")
        viewModel.setGender(user.gender ?: "")
    }

    val items = listOf("Hombre", "Mujer", "Prefiero no Especificar")



    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val (title, button, columnForm) = createRefs()

        TitleScreen(
            "Editar perfil",
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 15.dp)
                start.linkTo(parent.start)
            }
        )
        rememberScrollState()

        Column(
            modifier = Modifier
                .constrainAs(columnForm) {
                    top.linkTo(title.bottom, 20.dp)
                    bottom.linkTo(button.top, 20.dp) // Key fix: Espacio definido
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
            FieldForm(
                "Nombre", "Escribe tu nombre", name.value, { viewModel.setName(it) },
                isError = name.isError,
                errorDescription = "Tu nombre no debe contener números ni caracteres especiales",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            FieldForm(
                "Edad", "Escribe tu edad", age.value, { viewModel.setAge(it) },
                isError = age.isError,
                errorDescription = "Tu edad no debe contener letras ni caracteres especiales, Ejemplo: 23",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            FieldForm(
                "Altura", "Escribe tu altura", heightUser.value, { viewModel.setHeight(it) },
                isError = heightUser.isError,
                errorDescription = "Debes ingresar tu altura en centimetros, Ejemplo: 175",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            FieldForm(
                "Peso", "Escribe tu peso", weight.value, { viewModel.setWeight(it) },
                isError = weight.isError,
                errorDescription = "Ingresa tu peso en Kg, Ejemplo: 70",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            DropBox(
                modifier = Modifier.padding(bottom = 10.dp),
                items = items,
                selectedItem = gender.value,
                title = "Género",
                onItemSelected = { viewModel.setGender(it) }
            )
        }

        EstandardButton(
            text = "Guardar",
            enabled = user != null && viewModel.EnabledButton(),
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(columnForm.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                },
            onClick = {
                user?.let { // 👈 Acción solo si el usuario existe
                    viewModel.updateUserProfile(it)
                    onSaveChangesClick()
                }

            }
        )


    }
}

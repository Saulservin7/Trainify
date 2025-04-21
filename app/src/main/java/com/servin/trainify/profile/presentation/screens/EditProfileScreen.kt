package com.servin.trainify.profile.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.presentation.components.FieldForm
import com.servin.trainify.presentation.components.DropBox
import com.servin.trainify.presentation.components.EstandardButton
import com.servin.trainify.presentation.components.TitleScreen
import com.servin.trainify.profile.ProfileState
import com.servin.trainify.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {


    val name by viewModel.name.collectAsState()
    val age by viewModel.age.collectAsState()
    val height by viewModel.height.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val gender by viewModel.gender.collectAsState()


    val user = (viewModel.state as? ProfileState.Data)?.user
    if (user != null && name.value.isEmpty() && age.value.isEmpty() && height.value.isEmpty() && weight.value.isEmpty() && gender.value.isEmpty()) {
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
            .padding(16.dp)
    ) {
        val (title, button, columnForm) = createRefs()

        TitleScreen(
            "Editar perfil",
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Column(modifier = Modifier.constrainAs(columnForm) {
            top.linkTo(title.bottom, margin = 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
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
                "Altura", "Escribe tu altura", height.value, { viewModel.setHeight(it) },
                isError = height.isError,
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
                modifier = Modifier.padding(10.dp),
                items = items,
                selectedItem = gender.value,
                title = "Género",
                onItemSelected = { viewModel.setGender(it) }
            )


        }




        user?.let { viewModel.verifyData(it) }?.let {
            EstandardButton(
                text = "Guardar",
                enabled = !it,
                modifier = Modifier
                    .constrainAs(button) {
                        top.linkTo(columnForm.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    if (!viewModel.verifyData(user)) {
                        viewModel.updateUserProfile(user)
                    }


                }
            )
        }


    }
}



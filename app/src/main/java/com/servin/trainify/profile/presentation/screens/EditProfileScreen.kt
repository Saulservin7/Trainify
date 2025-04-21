package com.servin.trainify.profile.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
    val nameError by viewModel.errorName.collectAsState()
    val age by viewModel.age.collectAsState()
    val height by viewModel.height.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val gender by viewModel.gender.collectAsState()


    val user = (viewModel.state as? ProfileState.Data)?.user
    if (user != null && name.isEmpty() && age.isEmpty() && height.isEmpty() && weight.isEmpty() && gender.isEmpty()) {
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
                "Nombre", "Escribe tu nombre", name, { viewModel.setName(it) },
                isError = nameError,
                errorDescription = "Tu nombre no debe contener números ni caracteres especiales",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            FieldForm(
                "Edad", "Escribe tu edad",age, { viewModel.setAge(it) },
                isError = false,
                errorDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            FieldForm(
                "Altura", "Escribe tu altura", height, { viewModel.setHeight(it) },
                isError = false,
                errorDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            FieldForm(
                "Peso", "Escribe tu peso", weight, { viewModel.setWeight(it) },
                isError = false,
                errorDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            DropBox(
                modifier = Modifier.padding(10.dp),
                items = items,
                selectedItem = gender,
                title = "Género",
                onItemSelected = { viewModel.setGender(it) }
            )


        }

        EstandardButton(
            text = "Guardar",
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(columnForm.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = {
                if (user != null) {
                    viewModel.updateUserProfile(user)
                }
            }
        )


    }
}



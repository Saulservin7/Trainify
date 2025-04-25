package com.servin.trainify.profile.presentation.screens
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.servin.trainify.presentation.components.EstandardButton
import com.servin.trainify.presentation.components.ProfilePhoto
import com.servin.trainify.presentation.components.TitleScreen
import com.servin.trainify.presentation.components.UserInformation
import com.servin.trainify.profile.ProfileState
import coil3.compose.rememberAsyncImagePainter
import com.servin.trainify.R
import com.servin.trainify.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditProfileClick: () -> Unit = {}


    ) {
    ProfileContent(viewModel,onEditProfileClick)
}


@Composable
fun ProfileContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditProfileClick: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    val isDataComplete = viewModel.isCompleteData.collectAsState()
    val photoUrl = viewModel.photoUrl.collectAsState()
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { viewModel.uploadPhoto(it) }
        }
    )




    val buttonText = if (!isDataComplete.value) {
        "Completar perfil"
    } else {
        "Editar perfil"
    }


    val user = (viewModel.state as? ProfileState.Data)?.user
    val userData = listOf(
        "Edad:" to (user?.age ?: "No especificado"),
        "Altura:" to (user?.height?.plus(" m") ?: "No especificado"),
        "Peso:" to (user?.weight?.plus(" kg") ?: "No especificado"),
        "Genero" to (user?.gender ?: "No especificado")
    )


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        val (title, profilePhoto, userInformation, name, email, editButton, finishData) = createRefs()
        TitleScreen(
            title = "Perfil",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start) // Alineado a la izquierda
                }
                .padding(16.dp),

            )

        val photo = photoUrl.value


        ProfilePhoto(
            imageUrl = photo,
            onEditClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.constrainAs(profilePhoto) {
                top.linkTo(title.bottom, margin = 26.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            user?.name ?: "Cargando...",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(profilePhoto.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(email.top)
                }

        )

        Text(
            user?.email ?: "Cargando...",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .constrainAs(email) {
                    top.linkTo(name.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(userInformation.top)
                }
        )



        UserInformation(modifier = Modifier.constrainAs(userInformation) {
            top.linkTo(profilePhoto.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(editButton.top)

        }, userData = userData)

        if (!isDataComplete.value) {


            Text(
                "Completa tu perfil para obtener recomendaciones personalizadas e información más precisa.",
                color = Color.Red,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(finishData) {
                        top.linkTo(userInformation.bottom, margin = 6.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(editButton.top)
                    }
            )
        }


        EstandardButton(
            buttonText,
            modifier = Modifier.constrainAs(editButton) {
                top.linkTo(userInformation.bottom, margin = 6.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            onClick = { onEditProfileClick() },
        )
    }


}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
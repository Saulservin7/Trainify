package com.servin.trainify.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.servin.trainify.R
import com.servin.trainify.ui.theme.BluePrimary

@Composable
fun ProfilePhoto(
    imagePainter: Painter = painterResource(id = R.drawable.brazos),
    onEditClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val scaleFactor = screenWidth / 430.dp // ← aquí usamos el ancho base de Figma

    val imageSize = 150.dp * scaleFactor
    val editIconSize = imageSize * 0.25f

    Box(modifier = modifier.size(imageSize)) {
        Image(
            painter = imagePainter,
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .size(editIconSize)
                .align(Alignment.BottomEnd)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar foto",
                tint = BluePrimary,
                modifier = Modifier.size(editIconSize * 0.6f)
            )
        }
    }
}


@Composable
@Preview

fun ProfilePhotoPreview(){
    ProfilePhoto()
}
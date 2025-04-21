package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.servin.trainify.ui.theme.BluePrimary

@Composable
fun EstandardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier

    ) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.5f) // 80% del ancho de pantalla
            .aspectRatio(4.5f), // 270 / 60 = 4.5

        colors = ButtonDefaults.buttonColors(
            containerColor = BluePrimary
        )
    ) {
        androidx.compose.material3.Text(
            text = text,
            color = Color.White
        )
    }
}




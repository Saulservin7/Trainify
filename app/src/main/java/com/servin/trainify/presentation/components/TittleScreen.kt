package com.servin.trainify.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable

fun TitleScreen(
    title: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Text(
        text = title,
        color = Color.White,
        modifier = modifier,
        fontSize = 20.sp
    )
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    TitleScreen(
        title = "Profile",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
    )
}
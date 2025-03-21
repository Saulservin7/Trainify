package com.servin.trainify.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)


val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Gray = Color(0xFF212121)
val BluePrimary = Color(0xFF006FE7)

val BlueSecondary = Color(0xff00bfff)

val colorStart = Color(0x50979797)
val colorEnd=Color(0x50484848)

// Gradiente horizontal (de izquierda a derecha)
val gradient = Brush.linearGradient(
    colors = listOf(
        colorStart,
        colorEnd           // Final (100% opacidad)
    ),
    start = Offset.Zero,  // Punto de inicio (izquierda-centro)
    end = Offset.Infinite     // Punto final (derecha-centro)
)
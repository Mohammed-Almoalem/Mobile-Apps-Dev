package com.example.todo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// light theme colors

val lightColors = lightColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF000000),
    onSecondary = Color(0xFF2D2D2D),
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    outline = Color.Black
)

// dark theme colors

val darkColors = darkColorScheme(
    primary = Color(0xFF101010),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFF101010),
    secondary = Color(0xFF000000),
    onSecondary = Color(0xFF4D4D4D),
    onBackground = Color.Red,
    outline = Color.White
)

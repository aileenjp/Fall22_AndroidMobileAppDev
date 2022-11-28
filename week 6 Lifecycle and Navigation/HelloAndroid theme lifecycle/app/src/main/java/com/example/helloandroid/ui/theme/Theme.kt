package com.example.helloandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val CMULightColorPalette = lightColors(
    primary = CMUred,
    primaryVariant = CMUredvariant,
    onPrimary = Color.White,
    secondary = CMUreddark
)

@Composable
fun CMUTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
    val themeColors = if (darkTheme) {
        CMULightColorPalette  //use dark color palette here
    } else {
        CMULightColorPalette
    }
    MaterialTheme(
        colors = themeColors,
        typography = CMUTypography,
        shapes = CMUShapes,
        content = content
    )
}

//@Composable
//fun HelloAndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }
//
//    MaterialTheme(
//        colors = colors,
//        typography = Typography,
//        shapes = Shapes,
//        content = content
//    )
//}
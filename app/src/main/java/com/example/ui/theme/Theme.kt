package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CandyColorScheme = darkColorScheme(
    primary = CandyPink,
    secondary = CandyCyan,
    tertiary = CandyYellow,
    background = DeepPurpleBg,
    surface = DarkPurpleCard,
    surfaceVariant = SurfacePurple,
    onPrimary = TextPrimary,
    onSecondary = DeepPurpleBg,
    onTertiary = DeepPurpleBg,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary
)

@Composable
fun CandyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CandyColorScheme,
        typography = Typography,
        content = content
    )
}

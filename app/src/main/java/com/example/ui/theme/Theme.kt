package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AstraDarkColorScheme = darkColorScheme(
    primary = MarsRed,
    onPrimary = Color.White,
    primaryContainer = MarsRedDark,
    onPrimaryContainer = MarsRedGlow,
    secondary = SaturnBlue,
    onSecondary = TacticalObsidian,
    secondaryContainer = SaturnBlueDark,
    onSecondaryContainer = SaturnBlueGlow,
    tertiary = BudhadityaGold,
    onTertiary = TacticalObsidian,
    tertiaryContainer = BudhadityaGoldDark,
    onTertiaryContainer = BudhadityaGoldLight,
    background = TacticalObsidian,
    onBackground = TextPrimary,
    surface = TacticalSurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = TacticalSurfaceCard,
    onSurfaceVariant = TextSecondary,
    outline = TacticalBorder
)

// Tactical app is authoritative dark mode first
private val AstraLightColorScheme = AstraDarkColorScheme

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Enforce unified tactical aesthetic
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = AstraDarkColorScheme,
        typography = Typography,
        content = content
    )
}

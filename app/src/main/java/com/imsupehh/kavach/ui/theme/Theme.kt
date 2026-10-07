package com.imsupehh.kavach.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Citizen Color Scheme (Red Urgent & Dark Surface)
private val CitizenDarkColorScheme = darkColorScheme(
    primary = KavachRedPrimary,
    onPrimary = Color.White,
    primaryContainer = KavachRedDark,
    onPrimaryContainer = Color.White,
    secondary = KavachRedGlow,
    onSecondary = Color.White,
    background = DarkBackground,
    onBackground = TextWhitePrimary,
    surface = DarkSurface,
    onSurface = TextWhitePrimary,
    surfaceVariant = DarkCard,
    onSurfaceVariant = TextWhiteSecondary,
    outline = DarkBorder,
    error = KavachRedPrimary,
    onError = Color.White
)

private val CitizenLightColorScheme = lightColorScheme(
    primary = KavachRedPrimary,
    onPrimary = Color.White,
    primaryContainer = KavachRedLight,
    onPrimaryContainer = KavachRedDark,
    secondary = KavachRedGlow,
    onSecondary = Color.White,
    background = LightBackground,
    onBackground = TextDarkPrimary,
    surface = LightSurface,
    onSurface = TextDarkPrimary,
    surfaceVariant = LightCard,
    onSurfaceVariant = TextDarkSecondary,
    outline = LightBorder,
    error = KavachRedPrimary,
    onError = Color.White
)

// Authority Color Scheme (Tactical Navy & Official Blue)
private val AuthorityColorScheme = darkColorScheme(
    primary = AuthorityBluePrimary,
    onPrimary = Color.White,
    primaryContainer = AuthorityBlueDark,
    onPrimaryContainer = Color.White,
    secondary = AuthorityBlueAccent,
    onSecondary = Color.Black,
    background = AuthorityNavyBackground,
    onBackground = TextWhitePrimary,
    surface = AuthorityNavySurface,
    onSurface = TextWhitePrimary,
    surfaceVariant = AuthorityNavyCard,
    onSurfaceVariant = TextWhiteSecondary,
    outline = DarkBorder,
    error = SeverityHigh,
    onError = Color.White
)

@Composable
fun KaVachTheme(
    isAuthorityTheme: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isAuthorityTheme -> AuthorityColorScheme
        darkTheme -> CitizenDarkColorScheme
        else -> CitizenLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.upsaclay.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.upsaclay.core.ui.theme.GedoiseColor.Error
import com.upsaclay.core.ui.theme.GedoiseColor.InputBackground
import com.upsaclay.core.ui.theme.GedoiseColor.Primary
import com.upsaclay.core.ui.theme.GedoiseColor.PrimaryVariant
import com.upsaclay.core.ui.theme.GedoiseColor.Secondary
import com.upsaclay.core.ui.theme.GedoiseColor.Tertiary
import com.upsaclay.core.ui.theme.GedoiseColor.White


private val LightColorScheme = lightColorScheme (
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    primaryContainer = Tertiary,
    background = White,
    error = Error,
    surface = White,
    surfaceVariant = InputBackground,
    secondaryContainer = PrimaryVariant,
    surfaceContainerHigh = PrimaryVariant,
)

private val DarkColorScheme = LightColorScheme

@Composable
fun GedoiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
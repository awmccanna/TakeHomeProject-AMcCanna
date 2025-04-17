package com.example.openeyetakehome_amccanna.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81B29A),
    onPrimary = Color.Black,
    secondary = Color(0xFFCC9966),
    onSecondary = Color.Black,
    tertiary = Color(0xFFF4A261),
    onTertiary = Color.Black,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFEFEFEF),
    background = Color(0xFF1B1B1B),


//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A7C59),
    onPrimary = Color.White,
    secondary = Color(0xFF996633),
    onSecondary = Color.White,
    tertiary = Color(0xFFE07A5F),
    onTertiary = Color.White,
    surfaceVariant = Color(0xFFF0EAE2),
    onSurfaceVariant = Color(0xFF333333),
    background = Color(0xFFFAF8F5),

//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun OpeneyeTakeHomeAMcCannaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
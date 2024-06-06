package com.bangkit.luminasense.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bangkit.luminasense.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController


val LatoFontFamily = FontFamily(
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_bold, FontWeight.SemiBold),
    Font(R.font.lato_regular, FontWeight.Medium),
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_light, FontWeight.Light)
)

private val DarkColorScheme = darkColorScheme(
    primary = kMainColor,
    secondary = kSecondaryColor,
    tertiary = kAccentColor,
    background = kDarkColor,
    surface = kBlackColor,
    onPrimary = kWhiteColor,
    onSecondary = kWhiteColor,
    onTertiary = kBlackColor,
    onBackground = kWhiteColor,
    onSurface = kWhiteColor
)

private val LightColorScheme = lightColorScheme(
    primary = kMainColor,
    secondary = kSecondaryColor,
    tertiary = kAccentColor,
    background = kDarkColor,
    surface = kWhiteColor,
    onPrimary = kBlackColor,
    onSecondary = kBlackColor,
    onTertiary = kWhiteColor,
    onBackground = kBlackColor,
    onSurface = kBlackColor
)



@Composable
fun LuminaSenseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
    val view = LocalView.current
    val systemUiController = rememberSystemUiController()

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            WindowCompat.setDecorFitsSystemWindows(window, false)
            if (darkTheme) {
                systemUiController.setSystemBarsColor(
                    color = kDarkColor
                )
            } else {
                systemUiController.setSystemBarsColor(
                    color = kDarkColor
                )
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
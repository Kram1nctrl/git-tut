package com.kram1nctrl.gittut.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

// Git-inspired brand palette.
private val GitOrange = Color(0xFFC2410C)
private val GitOrangeLight = Color(0xFFFFB59B)
private val Ink = Color(0xFF1C1917)
private val InkSoft = Color(0xFF57433C)
private val Paper = Color(0xFFFDF7F4)
private val PaperDark = Color(0xFF161211)

/** Fixed dark surface used for code samples in both light and dark themes. */
val CodeBackground = Color(0xFF221D1B)
val CodeText = Color(0xFFF6EEE9)
val CodeMuted = Color(0xFFB7A39B)

private val LightColors = lightColorScheme(
    primary = GitOrange,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE1D6),
    onPrimaryContainer = Color(0xFF3B0A00),
    secondary = Color(0xFF4A5568),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE7E2DF),
    onSecondaryContainer = Color(0xFF1F2A37),
    tertiary = Color(0xFF0F766E),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFCFF5EF),
    onTertiaryContainer = Color(0xFF00201C),
    background = Paper,
    onBackground = Ink,
    surface = Color(0xFFFFFDFC),
    onSurface = Ink,
    surfaceVariant = Color(0xFFF2E7E2),
    onSurfaceVariant = InkSoft,
    outline = Color(0xFF8C7167),
    outlineVariant = Color(0xFFE3D3CC),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
)

private val DarkColors = darkColorScheme(
    primary = GitOrangeLight,
    onPrimary = Color(0xFF5F1600),
    primaryContainer = Color(0xFF862200),
    onPrimaryContainer = Color(0xFFFFDBCE),
    secondary = Color(0xFFBCC6D6),
    onSecondary = Color(0xFF26313F),
    secondaryContainer = Color(0xFF3A3532),
    onSecondaryContainer = Color(0xFFE9E1DD),
    tertiary = Color(0xFF7FD8CC),
    onTertiary = Color(0xFF003731),
    tertiaryContainer = Color(0xFF005049),
    onTertiaryContainer = Color(0xFFCFF5EF),
    background = PaperDark,
    onBackground = Color(0xFFF1E5E0),
    surface = Color(0xFF1D1918),
    onSurface = Color(0xFFF1E5E0),
    surfaceVariant = Color(0xFF2C2523),
    onSurfaceVariant = Color(0xFFD8C2B8),
    outline = Color(0xFFA08B81),
    outlineVariant = Color(0xFF443936),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
)

private val AppTypography = Typography(
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 25.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 21.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 17.sp),
    titleMedium = TextStyle(fontSize = 16.sp, lineHeight = 22.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold),
)

@Composable
fun GitTutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content,
    )
}

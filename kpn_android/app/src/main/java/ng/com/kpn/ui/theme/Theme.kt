package ng.com.kpn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val KpnPrimaryGreen = Color(0xFF28A745)
val KpnDarkGreen = Color(0xFF1B5E3F)
val KpnBlue = Color(0xFF0066CC)
val KpnLightGray = Color(0xFFF8F9FA)
val KpnDarkGray = Color(0xFF343A40)
val KpnBackgroundDark = Color(0xFF121212)

private val LightColorScheme = lightColorScheme(
    primary = KpnPrimaryGreen,
    onPrimary = Color.White,
    secondary = KpnBlue,
    onSecondary = Color.White,
    background = KpnLightGray,
    surface = Color.White,
    onBackground = KpnDarkGray,
    onSurface = KpnDarkGray
)

private val DarkColorScheme = darkColorScheme(
    primary = KpnPrimaryGreen,
    onPrimary = Color.White,
    secondary = KpnBlue,
    onSecondary = Color.White,
    background = KpnBackgroundDark,
    surface = KpnDarkGray,
    onBackground = KpnLightGray,
    onSurface = KpnLightGray
)

@Composable
fun KpnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

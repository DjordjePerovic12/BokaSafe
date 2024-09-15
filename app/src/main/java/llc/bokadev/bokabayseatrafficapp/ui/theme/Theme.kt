package llc.bokadev.bokabayseatrafficapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun BokaBaySeaTrafficAppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalBokaBaySeaTrafficAppColors provides bokaBaySeaTrafficAppColors,
        LocalBokaBaySeaTrafficTypography provides bokaBaySeaTafficTypography,
        content = content
    )
}

object BokaBaySeaTrafficAppTheme {
    val colors: BokaBaySeaTrafficAppColors
        @Composable get() = LocalBokaBaySeaTrafficAppColors.current
    val typography: BokaBaySeaTrafficTypography
        @Composable get() = LocalBokaBaySeaTrafficTypography.current
}
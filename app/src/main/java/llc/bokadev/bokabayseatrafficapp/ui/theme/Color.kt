package llc.bokadev.bokabayseatrafficapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val DarkBlue = Color(0xFF001E31)
val LightBlue = Color(0xFFB9D9EB)
val DarkBlueHeader = Color(0xFF222B45)
val Red = Color(0xFFF16364)
val White = Color(0xFFFFFFFF)
val Gray = Color(0xFFE6E6E6)
val GrayButton = Color(0xFFDEDEDE)
val GrayBorder = Color(0xFFC3C3C3)
val DarkGreen = Color(0xFF008675)
val LightGreen = Color(0xFFADDFB3)
val LightGray = Color(0xFFEFEFEF)
val SpacerGray = Color(0xB7B7B7B7)
val PickLocationBarGray = Color(0xFFF6F6F4)
val OrderABuggyPickLocationBarGray = Color(0xFF1E1E1E)
val LightBlueLowAlpha = Color(0x6FB9D9EB)
val CountriesSearchFieldColor = Color(0xFFE9E9E9)


@Immutable
data class BokaBaySeaTrafficAppColors(
    val darkBlue: Color,
    val lightBlue: Color,
    val darkBlueHeader: Color,
    val red: Color,
    val white: Color,
    val gray: Color,
    val grayButton: Color,
    val grayBorder: Color,
    val darkGreen: Color,
    val lightGreen: Color,
    val lightGray: Color,
    val pickLocationBarGray: Color,
    val spacerGray: Color,
    val lightBlueLowAlpha: Color,
    val countriesSearchFieldColor: Color
)

val LocalBokaBaySeaTrafficAppColors = staticCompositionLocalOf {
    BokaBaySeaTrafficAppColors(
        darkBlue = Color.Unspecified,
        lightBlue = Color.Unspecified,
        darkBlueHeader = Color.Unspecified,
        red = Color.Unspecified,
        white = Color.Unspecified,
        gray = Color.Unspecified,
        grayButton = Color.Unspecified,
        grayBorder = Color.Unspecified,
        darkGreen = Color.Unspecified,
        lightGreen = Color.Unspecified,
        lightGray = Color.Unspecified,
        pickLocationBarGray = Color.Unspecified,
        spacerGray = Color.Unspecified,
        lightBlueLowAlpha = Color.Unspecified,
        countriesSearchFieldColor = Color.Unspecified
    )
}

val bokaBaySeaTrafficAppColors = BokaBaySeaTrafficAppColors(
    darkBlue = DarkBlue,
    lightBlue = LightBlue,
    darkBlueHeader = DarkBlueHeader,
    red = Red,
    white = White,
    gray = Gray,
    grayButton = GrayButton,
    grayBorder = GrayBorder,
    darkGreen = DarkGreen,
    lightGreen = LightGreen,
    lightGray = LightGray,
    pickLocationBarGray = PickLocationBarGray,
    spacerGray = SpacerGray,
    lightBlueLowAlpha = LightBlueLowAlpha,
    countriesSearchFieldColor = CountriesSearchFieldColor
)
